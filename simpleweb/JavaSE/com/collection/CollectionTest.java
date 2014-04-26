package com.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class CollectionTest {

	void test() {

	}

	static void arrayToList() {
		List<String> list = new ArrayList<String>();
		String[] strs = new String[list.size()];
		// Collections.
		list.toArray(strs);

		arrayToList(strs);
		arrayToList("");
	}

	static void arrayToList(String... strs) {
		System.out.println("multiple parameter.");
		List<String> wordList = Arrays.asList(strs);
	}

	static void arrayToList(String strs) {
		System.out.println("single parameter.");
	}

	static Object[] arrayLen() {
		return new Object[] { null, null };
	}

	static void mapEntry() {
		HashMap<Long, Boolean> purchasedGoodsIdsMap = new HashMap<Long, Boolean>();
		purchasedGoodsIdsMap.put(1L, false);

		Set<Entry<Long, Boolean>> entrySet = purchasedGoodsIdsMap.entrySet();

		Long[] ids = new Long[entrySet.size()];
		int index = 0;
		Iterator<Entry<Long, Boolean>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<Long, Boolean> entry = it.next();
			if (!entry.getValue()) {
				System.out.println("......");
				ids[index++] = entry.getKey();
			}
		}

	}

	public void ff(Long... l) {
		System.out.println(l);
		System.out.println(Arrays.asList(l)); // [1, 2, 3, 4]
	}

	public void f(long... l) {
		System.out.println(l);
		System.out.println(Arrays.asList(l));
	}

	@Test
	public void testF() {
		Long[] p = new Long[] { 1L, 2L, 3L, 4L };
		long[] p1 = new long[] { 1L, 2L, 3L, 4L };

		ff(p);
		f(p1);

		f();
		f(null);

		ff();
		// ff(null); //null Exception
	}

	public static void main(String[] args) {
		// arrayToList();
		// System.out.println(arrayLen().length);
		// mapEntry();

		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(11L);
		ids.add(111L);

		Long[] idArr = new Long[ids.size()];
		ids.toArray(idArr);

		System.out.println(idArr);

		if (idArr instanceof Long[]) {
			System.out.println("Long[]");
		}
	}

}
