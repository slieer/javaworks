package com.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch的一个非常典型的应用场景是：
 * 有一个任务想要往下执行，但必须要等到其他的任务执行完毕后才可以继续往下执行。
 * 假如我们这个想要继续往下执行的任务调用一个CountDownLatch对象的await()方法，
 * 其他的任务执行完自己的任务后调用同一个CountDownLatch对象上的countDown()方法，
 * 这个调用await()方法的任务将一直阻塞等待，直到这个CountDownLatch对象的计数值减到0为止。
 
      举个例子，有三个工人在为老板干活，这个老板有一个习惯，就是当三个工人把一天的活都干完了的时候，他就来检查所有工人所干的活。
      记住这个条件：三个工人先全部干完活，老板才检查。
      所以在这里用Java代码设计两个类，Worker代表工人，Boss代表老板，具体的代码实现如下：
 
 * @author slieer
 * 2012-8-29 上午11:54:22
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch latch = new CountDownLatch(3);

        Worker_ w1 = new Worker_(latch, "张三");
        Worker_ w2 = new Worker_(latch, "李四");
        Worker_ w3 = new Worker_(latch, "王二");

        Boss boss = new Boss(latch);

        executor.execute(w3);
        executor.execute(w2);
        executor.execute(w1);
        executor.execute(boss);

        executor.shutdown();

    }
}

class Worker_ implements Runnable {

    private CountDownLatch downLatch;
    private String name;

    public Worker_(CountDownLatch downLatch, String name) {
        this.downLatch = downLatch;
        this.name = name;
    }

    public void run() {
        this.doWork();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException ie) {
        }
        System.out.println(this.name + "活干完了！");
        this.downLatch.countDown();

    }

    private void doWork() {
        System.out.println(this.name + "正在干活!");
    }

}

class Boss implements Runnable {

    private CountDownLatch downLatch;

    public Boss(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    public void run() {
        System.out.println("老板正在等所有的工人干完活......");
        try {
            this.downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("工人活都干完了，老板开始检查了！");
    }

}
