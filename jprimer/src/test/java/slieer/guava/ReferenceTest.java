package slieer.guava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

public class ReferenceTest {
	static void testSt(String s) {
		s += " abc";
	}

	static void testList(List<String> list) {
		list.add("tail");
	}

	@Test
	public void test() {
		String s = "123";
		testSt(s);
		System.out.println(s);

		List<String> list = new ArrayList<String>();
		list.add("a1");
		list.add("a2");
		list.add("a3");

		testList(list);
		
		List<String> immutableList = Collections.unmodifiableList(list);
		try {
			testList(immutableList); // throw exception.
			
		} catch (RuntimeException e) {
			System.out.println("exce:" + e.getMessage());
		}

		ImmutableList<String> defensiveCopy = ImmutableList.copyOf(list);
		
		try {
			testList(defensiveCopy);  // throw exception.
			
		} catch (RuntimeException e) {
			System.out.println("exce:" + e.getMessage());
		}
		
		
		System.out.println(list);
	}
}
