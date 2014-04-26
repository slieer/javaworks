package com.thread.executor;

public class ConcurrentCalculatorTest {
	public static void main(String... args) {
		ConcurrentCalculatorTest test = new ConcurrentCalculatorTest();
		//test.simpleTest();
		
		test.completeService();
	}

	int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 10, 11 };
	void simpleTest() {
		ConcurrentCalculator calc = new ConcurrentCalculator();
		Long sum = calc.sum(numbers);
		System.out.println(sum);
		calc.close();
	}
	
	void completeService(){
		ConcurrentCalculator2 calc = new ConcurrentCalculator2();
		Long sum = calc.sum(numbers);
		System.out.println(sum);
		calc.close();
	}
}
