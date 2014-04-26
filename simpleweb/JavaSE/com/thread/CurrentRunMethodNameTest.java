package com.thread;
public class CurrentRunMethodNameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = Thread.currentThread();
		StackTraceElement[] stack = thread.getStackTrace();
		String methodName = stack[0].getMethodName();
		System.out.println(methodName);
		
	}

}
