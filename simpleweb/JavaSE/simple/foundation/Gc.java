package simple.foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Gc {
	public static void main(String... args) {
		List<A> list = new ArrayList<A>();
		for (int i = 0; i < list.size(); i++) {
			list.add(new A(i));
		}
		list.clear();
		list = null;
		

		try {
			// System.gc();
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("thread");
					// Runtime.getRuntime().gc();
					System.gc();
					System.runFinalization();
					Runtime.getRuntime().gc();

				}
			});
			t.start();
			
			try {
				System.gc();
				System.runFinalization();
				Runtime.getRuntime().gc();
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}			
			
			System.out.println("aa");

			ExecutorService pool = Executors.newFixedThreadPool(3);
			for (int i = 0; i < 10; i++) {
				pool.execute(new Runnable() {
					public void run() {
						System.out.println("exec");
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	static class A {
		int i;

		public A() {
		}

		public A(int i) {

		}

		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			System.out.println("finalize, i:" + i);
		}
	}
}
