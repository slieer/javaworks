package com.thread;


/***
A daemon thread is a background thread.
It is subordinate to the thread that creates it.
When the thread that created the daemon thread ends, the daemon thread dies with it.

一个守护线程是一个后台线程，
它从属于创建它的线程
创建它的线程结束时，它自然结束
@author me
*
*/
class MyDaemon implements Runnable {
	Thread thrd;

	MyDaemon() {
		thrd = new Thread(this);
		thrd.setDaemon(true);
		thrd.start();
	}

	public boolean isDaemon() {
		return thrd.isDaemon();
	}

	public void run() {
		try {
			while (true) {
				System.out.print(".");
				Thread.sleep(100);
			}
		} catch (Exception exc) {
			System.out.println("MyDaemon interrupted.");
		}
	}
}

public class DaemonTest {
	public static void main(String args[]) throws Exception {
		MyDaemon dt = new MyDaemon();
		if (dt.isDaemon())
			System.out.println("dt is a daemon thread.");

		Thread.sleep(3000);
		System.out.println("\nMain thread ending.");
	}
}