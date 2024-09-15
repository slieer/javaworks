package slieer.guava;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import lombok.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class ObjectsTest {
	private static Logger logger = LogManager.getLogger(ObjectsTest.class
			.getName());

	@Test
	public void testToStr() {
		Person p = new Person();
		p.setFirstName("x");
		p.setLastName("xx");
		p.setZipCode(11);

		logger.info("logger: {}", p.toString());

		Person pp = new Person();
		pp.setFirstName("x");
		pp.setLastName("a");
		pp.setZipCode(11);

		logger.info("p compare pp : {}", p.compareTo(pp));
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	//生成该类下被final修饰或者non-null字段生成一个构造方法。
	@RequiredArgsConstructor
	@ToString()
	@EqualsAndHashCode(of={"zipCode", "firstName", "lastName"})
	public static class Person implements Comparable<Person> {
		@NonNull
		private String firstName;
		@NonNull
		private String lastName;
		private int age;
		private int zipCode;

		public int compareTo(Person that) {
			return ComparisonChain
					.start()
					.compare(this.firstName, that.firstName)
					.compare(this.lastName, that.lastName)
					.compare(this.zipCode, that.zipCode,
							Ordering.natural().nullsLast()).result();
		}
	}
}
