package com.proxy.simpleCat;

public class Cat implements IAnimal {
	public void info() {
		System.out.println("This is a cat!");
	}

	@Override
	public int otherInfo() {
		System.out.println("other info");
		return 0;
	}
}
