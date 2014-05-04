package com.slieer.guava;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


/**
 * Observer模式是常用的设计模式，比如改头换面叫个Listener
 * 今天，对于普通的应用，如果要使用Observer模式该如何做呢？答案是Guava的EventBus。
 * @author root
 */
public class EventBusTest {
	public static void main(String[] args) {
		test();
	}

	private static void f(){
		AsyncEventBus bus = new AsyncEventBus(Executors.newCachedThreadPool());	
	}
	
	/**
	 * Observable
	 */
	private static void test() {
		EventBus eventBus = new EventBus();
		eventBus.register(new MessageScreen());
		eventBus.post("Hello Screen");
		
		eventBus.post(Lists.newArrayList(1,2,3,4));
	}
	
	/**
	 * Observer
	 * @author root
	 */
	private static class MessageScreen {
		@Subscribe
		public void printMessage(String message) {
			System.out.println(message);
		}
		
		@Subscribe
		public void printMessage(List<Integer> message) {
			System.out.println(message);
		}
		
	}
}
