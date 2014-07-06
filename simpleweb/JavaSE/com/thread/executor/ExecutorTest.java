package com.thread.executor;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//scheduledExecutor();
		callable();
	}
	
	/**
	 * Future<V>代表一个异步执行的操作，
	 * 通过get()方法可以获得操作的结果，
	 * 如果异步操作还没有完成，则get()会使当前线程阻塞。
	 */
	public static void callable() {
		Callable<Integer> func = new Callable<Integer>() {
			public Integer call() throws Exception {
				System.out.println("inside callable");
				Thread.sleep(10);
				return Integer.valueOf(8);
			}
		};
		
		FutureTask<Integer> futureTask = new FutureTask<Integer>(func);
		Thread newThread = new Thread(futureTask);
		newThread.start();

		try {
			System.out.println("blocking here");
			Integer result = futureTask.get();
			System.out.println(result);
		} catch (InterruptedException ignored) {
			ignored.printStackTrace();
		} catch (ExecutionException ignored) {
			ignored.printStackTrace();
		}
	}

	public static void scheduledExecutor() {
		int corePoolSize = 5;
		Executor executor = Executors.newScheduledThreadPool(corePoolSize);

		ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;

		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println("hello");
			}
		};
		scheduler.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

	}

	public static void fixedThreadPool() {
		Executor executor = Executors.newFixedThreadPool(10);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println("task over");
				for (int i = 0; i < 10; i++) {
					System.out.println("task over" + i);
				}

				Scanner s = new Scanner(System.in);
				if (s.hasNext()) {
					String ss = s.nextLine();
					System.out.println(ss);
				}
			}
		};
		executor.execute(task);

	}
}
