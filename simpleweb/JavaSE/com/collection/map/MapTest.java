package com.collection.map;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.junit.Test;

final public class MapTest {
	static enum e{a,a1,a2};
	Map<String,Ob> map = new HashMap<String, Ob>();
	
	final static class Ob{
		
	}
	
	@Test
	public void testMap(){
	    Map<String, Long> a = new HashMap<String, Long>();
	    a.put("2", 2L);
	    a.put("22", 2L);
	    a.put("233", 2L);
	    
	    //null exception error.
	    long x = a.get(232423);
	}
	
	/**
	 * 
	 */
	@Test
	public void treeMapTest(){
	    TreeMap<String, String> tmp = new TreeMap<String, String>();
	    tmp.put("a", "aaa");
	    tmp.put("cd", "accc");
	    tmp.put("b", "bbb");
	    tmp.put("c", "ccc");
	    tmp.put("d", "cdc");
	    Iterator<String> iterator_2 = tmp.keySet().iterator();
	    while (iterator_2.hasNext()) {
	        Object key = iterator_2.next();
	        System.out.println("tmp.get(key) is :" + tmp.get(key));
	    }
	    
	    
	    tmp.entrySet();
	}
	
	public static void main(String[] args) {
		Map map = new HashMap();         // HashMap.Entry[]
		//LinkedHashMap ;WeakHashMap;IdentityHashMap
		
		//TreeMap.Entry<K,V>  &  Node in the Tree(自已，孩子，父) & 红黑树（Red-Black tree）
		Map tMap = new TreeMap();        
	    Map eMap = new EnumMap(e.class); //Object基本数组
	    
	    PriorityQueue queue = new PriorityQueue(); //Object[] & balanced binary heap
		
	}
}
