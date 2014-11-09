package com.beans.testBean;

import java.util.Arrays;

public class Month {
	private int value;
	private String name;
	private int[] days = { 11, 22, 33, 44, 55 };

	public Month(int v, String n) {
		value = v;
		name = n;
	}

	@Override
	public String toString() {
		return "Month [value=" + value + ", name=" + name + ", days="
				+ Arrays.toString(days) + "]";
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


	public int[] getDays() {
		return days;
	}

	public void setDays(int[] is) {
		days = is;
	}

}