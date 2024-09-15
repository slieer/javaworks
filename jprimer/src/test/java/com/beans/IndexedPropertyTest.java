package com.beans;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.beans.testBean.Month;

public class IndexedPropertyTest {

	public static void main(String[] args) {
		// describe();
	}

	static void getProperty(java.lang.Object bean, java.lang.String name) {
		Month month = new Month(1, "Jan");

		try {
			System.out.println(BeanUtils.getProperty(month, "value"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getIndexedProperty() {
		Month month = new Month(1, "Jan");

		try {
			System.out.println(BeanUtils.getIndexedProperty(month, "days", 1));
			System.out.println(BeanUtils.getIndexedProperty(month, "days[1]"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// populate(Object bean, Map properties)

	/**
	 * 这个方法返回一个Object中所有的可读属性，并将属性名/属性值放入一个Map中，另外还有
	 * 一个名为class的属性，属性值是Object的类名，事实上class是java.lang.Object的一个属性
	 */
	static void describe() {
		Month month = new Month(1, "Jan");

		try {
			Map<String, String> map = BeanUtils.describe(month);
			Set<String> keySet = map.keySet();
			for (Iterator<String> iter = keySet.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				System.out.println("KeyClass:" + element.getClass().getName());
				System.out.println("ValueClass:" + map.get(element).getClass().getName());
				System.out.print(element + "\t");
				System.out.print(map.get(element));
				System.out.println();
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e2) {
			e2.printStackTrace();
		} catch (NoSuchMethodException e3) {
			e3.printStackTrace();
		}
	}
}


