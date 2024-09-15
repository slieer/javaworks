
package com.thread;

import java.util.LinkedList;

public class WorkQueue {
    LinkedList<Integer> queue = new LinkedList<Integer>();

    // Add work to the work queue
    public synchronized void addWork(Integer o) {
        queue.addLast(o);
        //System.out.println(queue);
        notify();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public synchronized Integer getWork() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }
    
    public static class Worker extends Thread {
        // Special end-of-stream marker. If a worker retrieves
        // an Integer that equals this marker, the worker will terminate.
        public static final Integer NO_MORE_WORK = Integer.MAX_VALUE;

        WorkQueue q;

        Worker(WorkQueue q) {
            this.q = q;
        }
        
        public void run() {
            try {
                while (true) {
                    // Retrieve some work; block if the queue is empty
                    Object x = q.getWork();

                    // Terminate if the end-of-stream marker was retrieved
                    if (x == NO_MORE_WORK) {
                        break;
                    }

                    // Compute the square of x
                    int y = ((Integer)x).intValue() * ((Integer)x).intValue();
                    System.out.println("Thread id=" + getId() + ",Y=" + y);
                }
            } catch (InterruptedException e) {
            }
        }
    }
    
}
