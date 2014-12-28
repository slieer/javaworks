package com.guava.thread.minitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import com.google.common.util.concurrent.Monitor;


/**
 * http://www.javacodegeeks.com/2012/11/google-guava-synchronization-with-monitor.ht
 * 通过Monitor的Guard进行条件阻塞
 */
public class MonitorSample {
    private List<String> list = new ArrayList<String>();
    private static final int MAX_SIZE = 10;
    private Monitor monitor = new Monitor();
     
    private Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return list.size() < MAX_SIZE;
        }
    };
 
    @Test
    public void test(){
    	Runnable task = new Runnable() {
			@Override
			public void run() {
				String str = RandomStringUtils.random(3, "chars");
				try {
					addToList(str);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
    	
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        
        executorService.submit(task);
        //executorService.
    }
    
    public void addToList(String item) throws InterruptedException {
    	//Guard(形如Condition)，不满足则阻塞，而且我们并没有在Guard进行任何通知操作
        monitor.enterWhen(listBelowCapacity); 
        try {
            list.add(item);
        } finally {
            monitor.leave();
        }
    }
}