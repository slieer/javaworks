package simple.foundation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Thread.currentThread().s
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aa");
		basicRun();
		exec();
	}
	
	public static void basicRun(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread");
				// Runtime.getRuntime().gc();
			}
		});
		t.start();		
	}

	public static void exec(){
		ExecutorService pool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			pool.execute(new Runnable() {
				public void run() {
					System.out.println("exec");
				}
			});
		}		
	}
}
