package com.designPattern.singleInstance;

/**
 * 没有了锁的性能开销也可以多线程中使用，但是不能在第一次获取的时候才构造对象, 即延时加载对象
 * 改进{@link DoubleLockSingleton}
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class StaticSingleton {

	private static StaticSingleton instance = new StaticSingleton();

	private StaticSingleton() {
	}

	public static StaticSingleton getInstance() {
		return instance;
	}
}
