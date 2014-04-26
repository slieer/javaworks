package com.thread;

import org.junit.Test;

public class InterruptTest {
    /**
     * sleep/interrupt方式
     * 
     * @throws InterruptedException
     */
    @Test
    public void aTaskTest() throws InterruptedException {
        taskTest(new ATask());
    }
    
    /**
     * interrupt/interrupted 方式
     * 
     * @throws InterruptedException
     */
    @Test
    public void bTaskTest() throws InterruptedException {
        taskTest(new BTask());
    }
    
    /**
     * 上面两种方式结 合
     * */
    @Test
    public void cTaskTest() throws InterruptedException {
        taskTest(new CTask());
    }
    
    private void taskTest(Runnable r) throws InterruptedException {
        Thread t = new Thread(r);
        t.start();

        // 运行一断时间中断线程
        Thread.sleep(100);
        System.out.println("****************************");
        System.out.println("Interrupted Thread!");
        System.out.println("****************************");
        t.interrupt();
    }
}

class ATask implements Runnable {
    private double d = 0.0;

    public void run() {
        // 死循环执行打印"I am running!" 和做消耗时间的浮点计算
        try {
            while (true) {
                System.out.println("I am running!");

                for (int i = 0; i < 900000; i++) {
                    d = d + (Math.PI + Math.E) / d;
                }
                // 休眠一断时间,中断时会抛出InterruptedException
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Exce:" + e.getMessage());
        }
    }
}

class BTask implements Runnable {
    private double d = 0.0;

    public void run() {
        // 检查程序是否发生中断
        while (!Thread.interrupted()) {
            System.out.println("I am running!");

            for (int i = 0; i < 900000; i++) {
                d = d + (Math.PI + Math.E) / d;
            }
        }

        System.out.println("BTask.run() interrupted! ");
    }
}

class CTask implements Runnable {
    private double d = 0.0;

    public void run() {

        try {
            // 检查程序是否发生中断
            while (!Thread.interrupted()) {
                System.out.println("I am running!");
                // point1 before sleep
                Thread.sleep(20);
                // point2 after sleep
                //System.out.println("Calculating");
                for (int i = 0; i < 900000; i++) {
                    d = d + (Math.PI + Math.E) / d;
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Exce:" + e.getMessage());
        }

        System.out.println("CTask.run() interrupted!");
    }
}
