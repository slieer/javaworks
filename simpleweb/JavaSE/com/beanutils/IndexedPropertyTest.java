package com.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

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
			Map map = BeanUtils.describe(month);
			Set keySet = map.keySet();
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
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

class Month {
	private int value;
	private String name;
	private int[] days = { 11, 22, 33, 44, 55 };

	public Month(int v, String n) {
		value = v;
		name = n;
	}

	/**
	 * Returns the name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value.
	 * 
	 * @return int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *          The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *          The value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return value + "(" + name + ")";
	}

	public int[] getDays() {
		return days;
	}

	public void setDays(int[] is) {
		days = is;
	}

}
