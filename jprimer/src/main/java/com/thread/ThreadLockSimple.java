package com.thread;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * http://www.cnblogs.com/alphablox/archive/2013/01/13/2858264.html
 * @author slieer
 *
 * last modified: 2014年8月4日 下午5:15:15
 */
public class ThreadLockSimple {

    Lock lock = new ReentrantLock();
    Condition finishCondition = lock.newCondition();

    class T1 implements Runnable {

        public void run() {
            try {
                lock.lock();
                System.out.println("enter T1:" + new Date());
                try {
                    finishCondition.await();
                    System.out.println("end wating T1:" + new Date());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } finally {
                lock.unlock();
                System.out.println("exit T1:" + new Date());
            }

        }

    }

    class T2 implements Runnable {

        public void run() {
            try {
                lock.lock();
                System.out.println("enter T2:" + new Date());
                try {
                    Thread.sleep(1000);
                    finishCondition.signal();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } finally {
                lock.unlock();
                System.out.println("exit T2:" + new Date());
            }
        }
    }

    public void run() throws Exception {
        new Thread(new T1()).start();
        Thread.sleep(50);
        new Thread(new T2()).start();
    }

    public static void main(String[] args) throws Exception {
        ThreadLockSimple lt = new ThreadLockSimple();
        lt.run();
        Thread.sleep(3000);
        System.out.println("exit main.");
    }

}