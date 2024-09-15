package org.slieer.guava.collection;

import org.junit.jupiter.api.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

public class NewSetTest {
	
	/**
	 *  Map 	          Corresponding Multiset	Supports null elements
		HashMap 	      HashMultiset 	            Yes
		TreeMap 	      TreeMultiset 	            Yes (if the comparator does)
		LinkedHashMap 	  LinkedHashMultiset	    Yes
		ConcurrentHashMap ConcurrentHashMultiset 	No
		ImmutableMap 	  ImmutableMultiset 	    No 
	 */
	@Test
	public void testHashMultiset(){
		Multiset<String> m = HashMultiset.create();
		loadTestData(m);
		System.out.println(m);
		//[b, -1, c, a x 3]
	}

	@Test
	public void testLinkedHashMultiset(){
		Multiset<String> set = LinkedHashMultiset.create();
		loadTestData(set);
		
		System.out.println(set);
		//[a x 3, b, c, -1]
	}
	
	private void loadTestData(Multiset<String> m) {
		m.add("a");
		m.add("a");
		m.add("b");
		m.add("c");
		m.add("a");
		m.add("-1");
	}
	
}
