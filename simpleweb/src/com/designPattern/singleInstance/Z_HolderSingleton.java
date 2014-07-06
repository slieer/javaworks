package com.designPattern.singleInstance;

/**
 * 
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class Z_HolderSingleton {
	private static class HolderSingletonHolder {
		static Z_HolderSingleton instance = new Z_HolderSingleton();
	}

	private Z_HolderSingleton() {
		//特别注意：第一次构造函数失败将导致永远无法再次得到构建对象的机会
		// maybe throw an Exception when doing something
	}

	public static Z_HolderSingleton getInstance() {
		return HolderSingletonHolder.instance;
	}
}
