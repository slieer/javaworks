package com.annotion.simple;

public class KeyValueBean {
	@KeyValueAnnotation(key = "name", value = "xy")
	public void save() {
		System.out.println("save");
	}
}
