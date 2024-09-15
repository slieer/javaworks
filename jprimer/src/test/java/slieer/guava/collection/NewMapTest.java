package slieer.guava.collection;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class NewMapTest {
	/**
	 *  Implementation 	        Keys behave like... 	Values behave like..
		ArrayListMultimap 	    HashMap 	            ArrayList
		HashMultimap 	        HashMap 	            HashSet
		LinkedListMultimap* 	LinkedHashMap* 	        LinkedList*
		LinkedHashMultimap** 	LinkedHashMap 	        LinkedHashSet
		TreeMultimap 	        TreeMap 	            TreeSet
		ImmutableListMultimap 	ImmutableMap 	        ImmutableList
		ImmutableSetMultimap 	ImmutableMap 	        ImmutableSet
	 */
	@Test
	public void testArrayListMultimap(){
		Multimap<String, String> p = ArrayListMultimap.create();		
		loadTestData(p);
		
		System.out.println(p);
		//{1=[bb], 0=[bbb], a=[val, val, val], aa=[val, val]}.  
		//key is disorder, value can repeat.
	}
	
	@Test
	public void testHashMultimap(){
		Multimap<String, String> p = HashMultimap.create();
		loadTestData(p);
		System.out.println(p);
		//{1=[bb], 0=[bbb], a=[val], aa=[val]}
		//value cannot repeat.
	}

	@Test
	public void testLinkedListMultimap(){
		Multimap<String, String> p = LinkedListMultimap.create();
		loadTestData(p);
		System.out.println(p);
		//{a=[val, val, val], aa=[val, val], 1=[bb], 0=[bbb]}
		//key order enter order.
	}
	
	private Multimap<String, String> loadTestData(Multimap<String, String> p){
		p.put("a", "val");
		p.put("a", "val");
		p.put("a", "val");
		
		p.put("aa", "val");
		p.put("aa", "val");
		p.put("1", "bb");
		p.put("0", "bbb");
		
		
		return p;
	}
}

