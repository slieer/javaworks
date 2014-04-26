package com.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  采用reenterlock方式.
 *  线程A、线程B、线程C做如下的输出：
 *  ABC
    ABC
    ABC
    ABC
    ABC
    ABC
    ABC
    ABC
    ABC
    ABC

 * @author slieer 2013-6-17
 *
 */
public class ThreadLockTest {
     private static Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问的互斥
     private static int state = 0;
     
     static class ThreadA extends Thread {
         @Override
         public void run() {
             for (int i = 0; i < 10;) {
                 lock.lock();
                 if (state % 3 == 0) {
                     System.out.print("A");
                     state++;
                     i++;
                 }
                 lock.unlock();
             }
         }
     }
     
     static class ThreadB extends Thread {
         @Override
         public void run() {
             for (int i = 0; i < 10;) {
                 lock.lock();
                 if (state % 3 == 1) {
                     System.out.print("B");
                     state++;
                     i++;
                 }
                 lock.unlock();
             }
         }
     }
     
     static class ThreadC extends Thread {
         @Override
         public void run() {
             for (int i = 0; i < 10;) {
                 lock.lock();
                 if (state % 3 == 2) {
                     System.out.print("C");
                     state++;
                     i++;
                 }
                 lock.unlock();
             }
         }
     }
     
     public static void main(String[] args) {
         new ThreadA().start();
         new ThreadB().start();
         new ThreadC().start();
     }
     
 }