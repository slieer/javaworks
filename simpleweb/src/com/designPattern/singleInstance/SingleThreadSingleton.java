package com.designPattern.singleInstance;

/**
 * 最原始的单例模式, 多线程会导致多个实例出现
 * 
 * 改进 {@link SynchronizedSingleton}
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class SingleThreadSingleton {

	private static SingleThreadSingleton instance = null;

	private SingleThreadSingleton() {
	}

	public static SingleThreadSingleton getInstance() {
		if (instance == null) {
			instance = new SingleThreadSingleton();
		}
		return instance;
	}
}
