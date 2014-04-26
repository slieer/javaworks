package com.collection.set;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class SetTest {
	static enum e{a,a1,a2};

	public static void main(String[] args) {
		Set set = new TreeSet();  //内部采用TreeMap 实现,红黑树。
		set.toArray();
		Set hset = new HashSet();  //内部采用HashMap实现。
		//LinkedHashSet
		
		Set eSet =  EnumSet.noneOf(e.class);    //Enum[]
		Set<e> set2 = EnumSet.allOf(e.class);
		set2.toArray();
		Set<e> set3 = EnumSet.of(e.a1, e.a2);
		
	}
}
