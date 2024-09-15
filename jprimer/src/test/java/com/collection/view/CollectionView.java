package com.collection.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CollectionView {
	public void main(String... args) {
		"abcdef".substring(1, 3); // new String

		/*
		 * 改变数组大小的方法都抛出UnsupportedOperationException。
		 */
		Arrays.asList(new int[] { 1, 2 });

		/*
		 * immutable list consisting of copies of the specified object.
		 */
		List<String> settings = Collections.nCopies(5, "Baby");

		{

			// 返回子范围视图；
			List<String> tempList = new ArrayList<String>();
			tempList.add("s");
			tempList.add("s");
			tempList.add("s");

			/*
			 * private class SubList extends AbstractList<E> implements
			 * RandomAcces
			 */
			tempList.subList(3, 7);
		}
		

		{

			List<String>tempList = new LinkedList<String>();
			for (int i = 0; i < 10; i++) {
				tempList.add("" + i);
			}
			// 返回不可修改视图
			List<String> unmodifiableList = Collections
					.unmodifiableList(tempList);
		}
		
		

	}
}
