package com.security.classloader;

public class TestAction implements ActionInterface {

	public String action() {
		return "security.classloader.action ";
	}
	
	public static void main(String[] args) {
		System.out.println(new TestAction().action());
	}
}
