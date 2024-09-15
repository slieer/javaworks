package com.thread;


/**
 * 两个线程，从同一个队列中取数，然后计算平方数。
 * @author slieer 2013-6-17
 *
 */
public class WorkQueueTest {
	
	public static void main(String...args){
		testWorkQueue();
	}
	
	public static void testWorkQueue(){
	// Create the work queue
		WorkQueue queue = new WorkQueue();

		// Create a set of worker threads
		final int numWorkers = 2;
		WorkQueue.Worker[] workers = new WorkQueue.Worker[numWorkers];
		for (int i=0; i<workers.length; i++) {
		    workers[i] = new WorkQueue.Worker(queue);
		    workers[i].start();
		}

		// Add some work to the queue; block if the queue is full.
		// Note that null cannot be added to a blocking queue.
		for (int i=0; i<100; i++) {
		    queue.addWork(i);
		}

		// Add special end-of-stream markers to terminate the workers
		for (int i=0; i<workers.length; i++) {
		    queue.addWork(WorkQueue.Worker.NO_MORE_WORK);
		}
	}
}
