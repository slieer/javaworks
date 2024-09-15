package com.thread;

/**
 * 让一个线程执行结束，再去执行另外一个线程，最后才让主线程结束。
 * @author slieer
 * Create Date 2013-5-30
 * version 1.0
 */
class ThreadTesterA implements Runnable {
    private int counter;

    @Override
    public void run() {
        while (counter <= 10) {
            System.out.println("Counter = " + counter + " ");
            counter++;
        }
        System.out.println("==============================");
    }
}

class ThreadTesterB implements Runnable {
    private int i;

    @Override
    public void run() {
        while (i <= 10) {
            System.out.println("i = " + i + " ");
            i++;
        }
        System.out.println("==============================");
    }
}

public class JoinSimpleTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ThreadTesterA());
        Thread t2 = new Thread(new ThreadTesterB());
        t1.start();
        t1.join(); // wait t1 to be finished
        t2.start();
        t2.join(); // in this program, this may be removed
        
        System.out.println("Main Thread Finish.");
    }
}