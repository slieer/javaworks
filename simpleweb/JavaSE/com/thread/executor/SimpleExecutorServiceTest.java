package com.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleExecutorServiceTest {
	public static void main(String[] args) {
		PrintTask task1 = new PrintTask("thread1");
		PrintTask task2 = new PrintTask("thread2");
		PrintTask task3 = new PrintTask("thread3");

		System.out.println("Starting threads");

		ExecutorService threadExecutor = Executors.newCachedThreadPool();

		// start threads and place in runnable state
		threadExecutor.execute(task1); // start task1
		threadExecutor.execute(task2); // start task2
		threadExecutor.execute(task3); // start task3

		threadExecutor.shutdown(); // shutdown worker threads

		System.out.println("Threads started, main ends\n");
	}
}

class PrintTask implements Runnable {
	private int sleepTime;

	private String threadName;

	public PrintTask(String name) {
		threadName = name;
		sleepTime = 1000;
	}

	public void run() {
		try {
			System.out.printf("%s going to sleep for %d milliseconds.\n", threadName, sleepTime);

			Thread.sleep(sleepTime); // put thread to sleep
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
		System.out.printf("%s done sleeping\n", threadName);
	}
}