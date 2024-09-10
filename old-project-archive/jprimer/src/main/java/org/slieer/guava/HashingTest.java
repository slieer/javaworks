package org.slieer.guava;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

public class HashingTest {
	static Logger logger = LogManager.getLogger(GuavaStringTest.class.getName());

	public enum PersonFunnel implements Funnel<Person> {
		INSTANCE, PLUS, SLIEER;

		@Override
		public void funnel(Person person, PrimitiveSink into) {
			into.putString(person.getFirstName(), Charsets.UTF_8)
					.putString(person.getLastName(), Charsets.UTF_8)
					.putInt((Integer) person.getAge());
		}
	}

	@Test
	public void testMd5() {
		long id = 1;
		String name = "slieer";
		Person person = new Person("Zhai", "Xiaobin", 30, 123);

		// HashFunction hf = Hashing.md5();
		HashFunction hf = Hashing.crc32();
		// HashFunction hf = Hashing.murmur3_32();
		// HashFunction hf = Hashing.sha1();
		// HashFunction hf = Hashing.sipHash24();
		// HashFunction hf = Hashing.sha512();

		HashCode hc = hf.newHasher().putLong(id)
				.putString(name, Charsets.UTF_8)
				.putObject(person, PersonFunnel.INSTANCE).hash();

		logger.info("hashcode.md5 {}", hc.toString());

		HashCode hc1 = hf.newHasher().putObject(person, PersonFunnel.INSTANCE)
				.hash();

		logger.info("hashcode.md5 {}", hc1.toString());

	}

	/**
	 *Bloom filter 二进制向量数据结构，它具有很好的空间和时间效率，被用来检测一个元素是不是集合中的一个成员
	 */
	@Test
	public void testBloom() {
		ImmutableList<Person> list = ImmutableList.of(new Person(),
				new Person(), 
				new Person("firstName", "lastName", 2, 123), 
				new Person());

		BloomFilter<Person> friends = BloomFilter.create(PersonFunnel.INSTANCE,
				500, 0.01);
		for (Person friend : list) {
			friends.put(friend);
		}
		// much later
		Person dude = new Person("firstName", "lastName", 2, 123);
		if (friends.mightContain(dude)) {
			// the probability that dude reached this place if he isn't a friend
			// is 1%
			// we might, for example, start asynchronously loading things for
			// dude while we do a more expensive exact check
		}
	}
}
