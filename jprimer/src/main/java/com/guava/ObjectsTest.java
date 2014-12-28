package com.guava;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

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
}

class Person implements Comparable<Person> {
	private String firstName;
	private String lastName;
	private int age;
	private int zipCode;

	@Override
	public int hashCode() {
		// java7 Objects.hash(Object...).
		return Objects.hashCode(this.firstName, this.lastName, this.zipCode);
	}

	public int compareTo(Person that) {
		return ComparisonChain
				.start()
				.compare(this.firstName, that.firstName)
				.compare(this.lastName, that.lastName)
				.compare(this.zipCode, that.zipCode,
						Ordering.natural().nullsLast()).result();
	}

	public String toString() {
		return Objects.toStringHelper(this).add("x", 1)
				.add("firstName", this.firstName).toString();

		// // Returns "MyObject{x=1}"
		// Objects.toStringHelper("MyObject")
		// .add("x", 1)
		// .toString();
	}

	
	
	public Person() {
		super();
	}

	public Person(String firstName, String lastName, int age, int zipCode) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.zipCode = zipCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setAge(int age) {
		// TODO Auto-generated method stub
		this.age = age;
	}
	
	public Object getAge() {
		// TODO Auto-generated method stub
		return age;
	}

}
