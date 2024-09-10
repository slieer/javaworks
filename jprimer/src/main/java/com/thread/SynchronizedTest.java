package com.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dev
 *
 */
public class SynchronizedTest {
	static class Stack{
		private List<String> list = new ArrayList<String>();
		
		public synchronized void push(String value) {
			synchronized (this) {
				list.add(value);
				notify();
			}
		}
		
		public synchronized String pop() throws InterruptedException {
			synchronized (this) {
				if (list.size() <= 0) {
					wait();
				}
				return list.remove(list.size() - 1);
			}
		}		
	}
	
	
}
