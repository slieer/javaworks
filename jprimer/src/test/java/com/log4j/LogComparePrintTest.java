package com.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogComparePrintTest {
	private static final int COUNT_1000000 = 1000000;

	/**
	 * log所用时间:15900
	 * log所用时间:17496
	 */
	@Test
	public void testLog(){
		logger4j2();		
	}
	
	@Test
	public void testJavaLog(){		
		javaLog();		
	}
	/**
	 * print所用时间:6040
	 */
	@Test
	public void testPrint(){
		print();
	}
	
	public static void print() {
		long start = System.currentTimeMillis();

		for (int i = 0; i < COUNT_1000000; i++) {
			System.out.println("Syso输出" + i);
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("print所用时间:" +time);
	}

	private static Logger log = LogManager.getLogger("");

	public static void logger4j2() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNT_1000000; i++) {
			log.error("log输出" + i);
		}
		long time = System.currentTimeMillis() - start;
		log.info("log所用时间:" + time);
	}
	
	private static java.util.logging.Logger javaLoger = java.util.logging.Logger.getLogger("");	
	public static void javaLog(){
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNT_1000000; i++) {
			javaLoger.info("循环"+i+"次");
		}
		long time = System.currentTimeMillis()-start;
		log.info("时间是"+time);
	}	

}
