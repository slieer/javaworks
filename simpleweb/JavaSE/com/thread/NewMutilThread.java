package com.thread;


/**
 * Java在一个线程中可以启动另一个线程.
 * 统常情况: 执行Main方法就是开启了一个主线程.
 * @author slieer
 *
 */
public class NewMutilThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(r).start();
	}
	
	static Runnable r = new Runnable() {		
		@Override
		public void run() {
			System.out.println("abcdef");
			Thread t = new Thread(){
				public void run() {
					System.out.println("abc");					
				};
			};
			
			t.start();
		}
	};
}
