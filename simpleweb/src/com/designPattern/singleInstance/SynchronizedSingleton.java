package com.designPattern.singleInstance;

/**
 * 不可避免的有性能开销
 * 再改进 {@link StaticSingleton}
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class SynchronizedSingleton {

	private static SynchronizedSingleton instance = null;

	private SynchronizedSingleton() {
	}

	public static synchronized SynchronizedSingleton getInstance() {
		if (instance == null) {
			instance = new SynchronizedSingleton();
		}
		return instance;
	}
}
