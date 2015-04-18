package com.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 
 * Future要获取异步任务执行的结果，需要通过轮询或者阻塞等待的方式， 这样的方式，总显得不太‘完美’，我们知道，比较好的方式，
 * 应该是异步执行结束后，自动通知用户异步任务结束了，你可以通过Future来获取执行结果了。
 * 
 * google的ListenableFuture，用户可以向它注册一个回调函数和提供一个线程池(可选)，
 * 当异步任务执行结束后，它会自动在用户提供的线程池里调用用户注册的回调函数，通知用户异步任务执行结束了。
 * 
 * @author root
 */
public class ListeningExecutorTest {
	@Test
	public void test() {
		ListeningExecutorService executor = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(1));

		final ListenableFuture<String> future = executor
				.submit(new Callable<String>() {
					public String call() throws Exception {
						return "Hello listenable future";
					}
				});

		Futures.addCallback(future, new FutureCallback<String>() {
			public void onSuccess(String result) {
				System.out.println(result);
			}

			public void onFailure(Throwable t) {
				System.out.println("error: " + t);
			}

		}, Executors.newFixedThreadPool(1));

		System.out.println("exit..");
	}
	
	@Test
	public void test1() {
		ListeningExecutorService executor = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(1));

		final ListenableFuture<String> future = executor
				.submit(new Callable<String>() {
					public String call() throws Exception {
						return "Hello listenable future";
					}
				});

		future.addListener(new Runnable() {
			public void run() {
				try {
					System.out.println(future.get());
				} catch (InterruptedException e) {
					future.cancel(true);
				} catch (ExecutionException e) {
					future.cancel(true);
				}
			}
		}, executor);

		System.out.println("exit..");

	}
}
