package com.thread.executor.cancel;

/*
 @author:vc_java@hotmail.com
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * mayInterruptIfRunning设成false话，不允许在线程运行时中断，设成true的话就允许。
可以参考下面的代码来理解，如果设为false的话，会打印到99999，如果设成true的话，可能就打印不到99999 
 * 
 * 
 * @author slieer
 * Create Date 2013-10-8
 * version 1.0
 */
public class Future01 {

    public static void main(String[] args) {
        ExecutorService eService = Executors.newFixedThreadPool(5);
        Future<?> future = eService.submit(new RunFuture());
        try {
            Thread.sleep(500);
            future.cancel(true);
            System.out.println("haha---------------");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        eService.shutdown();
    }

}

class RunFuture implements Runnable {
    int i = 0;

    @Override
    public void run() {
        while (i < 100000 && !Thread.currentThread().isInterrupted()/**/) {
            System.out.println("i++:" + i);
            i++;
        }

    }

}