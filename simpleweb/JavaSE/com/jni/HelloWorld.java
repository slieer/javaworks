package com.jni;

import java.util.List;
import java.util.Map;

class HelloWorld {
	public native void displayHelloWorld();
	
	public native void display(String st);
	
	public native Map<Integer, String> display(List<String> st);

	static {
		System.loadLibrary("hello");

	}

	public static void main(String[] args) {
		new HelloWorld().displayHelloWorld();
	}
}
