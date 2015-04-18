package com.guava;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NewBiMapTest {
	/**
	Key-Value Map Impl 	Value-Key Map Impl 	Corresponding BiMap
	HashMap 	        HashMap 	        HashBiMap
	ImmutableMap 	    ImmutableMap 	    ImmutableBiMap
	EnumMap 	        EnumMap 	        EnumBiMap
	EnumMap 	        HashMap 	        EnumHashBiMap
	*/
	
	@Test
	public void testHashBiMap(){
		BiMap<String, Integer> userId = HashBiMap.create();
		
		userId.put("Bob", 42);
		//userId.put("Bo", 42);  put error.
		userId.put("Bob", 44);

		System.out.println(userId);
		Integer id = 42;
		String userForId = userId.inverse().get(id);
		System.out.println(userForId);

	}
}
