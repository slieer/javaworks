package com.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * ExecutoreService提供了submit()方法，传递一个Callable，或Runnable，返回Future。
 * 如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成。
 * 
 * @author borqs
 */
public class ConcurrentCalculator {

	private ExecutorService exec;
	private int cpuCoreNumber;
	private List<Future<Long>> tasks = new ArrayList<Future<Long>>();

	public ConcurrentCalculator() {
		cpuCoreNumber = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(cpuCoreNumber);
	}

	public Long sum(final int[] numbers) {
		// 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor
		for (int i = 0; i < cpuCoreNumber; i++) {
			int increment = numbers.length / cpuCoreNumber + 1;
			int start = increment * i;
			int end = increment * i + increment;
			if (end > numbers.length)
				end = numbers.length;
			SumCalculator subCalc = new SumCalculator(numbers, start, end);
			FutureTask<Long> task = new FutureTask<Long>(subCalc);
			tasks.add(task);
			if (!exec.isShutdown()) {
				exec.submit(task);
			}
		}
		return getResult();
	}

	/**
	 * 迭代每个只任务，获得部分和，相加返回
	 * @return
	 */
	private Long getResult() {
		Long result = 0l;
		for (Future<Long> task : tasks) {
			try {
				// 如果计算未完成则阻塞
				Long subSum = task.get();
				result += subSum;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void close() {
		exec.shutdown();
	}
}