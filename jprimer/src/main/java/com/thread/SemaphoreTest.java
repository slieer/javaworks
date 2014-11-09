package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore实现的功能就类似厕所有5个坑，
 * 假如有10个人要上厕所，那么同时只能有多少个人去上厕所呢？
 * 同时只能有5个人能够占用，当5个人中 的任何一个人让开后，其中等待的另外5个人中又有一个人可以占用了。
 * 另外等待的5个人中可以是随机获得优先机会，也可以是按照先来后到的顺序获得机会，这取决于构造Semaphore对象时传入的参数选项。
 * 
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 * 
 * @author slieer 2013-6-17
 *
 */
public class SemaphoreTest {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    
    // 只能5个线程同时执行
    private final static int THREAD_CONCURRENCY_MAX_AMOUNT = 5; 
    private final static Semaphore semp = new Semaphore(THREAD_CONCURRENCY_MAX_AMOUNT);

    public static void main(String[] args) {
        // 线程池
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            exec.execute(new Run(index));
        }
        // 退出线程池
        exec.shutdown();
    }
    
    static class Run implements Runnable {
        private int NO;
        public Run(int no){
            NO = no;
        }
        
        public void run() {
            try {
                // 获取许可
                semp.acquire();
                //System.out.println(semp.);
                System.out.println("Accessing: " + NO);
                Thread.sleep((long)(Math.random() * 5000));
                // 访问完后，释放
                semp.release();
                System.out.println("-----------------" + semp.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
