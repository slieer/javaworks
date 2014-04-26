package com.thread.executor;
import java.util.concurrent.Callable;

class SumCalculator implements Callable<Long> {
	private int[] numbers;
	private int start;
	private int end;

	public SumCalculator(final int[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	public Long call() throws Exception {
		Long sum = 0l;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
}
