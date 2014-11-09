package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  An application exits when there are no non-daemon threads running. 
 * @author me
 *
 */
class MyThread extends Thread {
  public void run() {
    boolean isDaemon = isDaemon();
    try {
    	System.out.println("isDaemon----------------:" + isDaemon);
			sleep(100);
			System.out.println("isDaemon:" + isDaemon);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
  }
}

/**
 * 伴随线程，在它主属线程结束时结束，没执行完也会结束。
 * @author me
 *
 */
public class DaemonTest1 {
  public static void main(String[] argv) throws Exception {
  	System.out.println("main thread start.");
  	Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("-----");
				
				Thread thread = new MyThread();
				thread.setDaemon(true);
				thread.start();
				
				System.out.println("-----123");
				try {
					Thread.sleep(10);
					//Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ExecutorService e = Executors.newCachedThreadPool();
		e.execute(runnable);
		e.shutdown();
		System.out.println("main thread end.");
  }
}