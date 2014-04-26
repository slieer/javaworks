package com.proxy.simpleCat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {
	public static void main(String[] args) {
		// 真实对象（即被代理对象）
		final IAnimal animal = new Cat();

		// 为真实对象制定一个调用处理器
		InvocationHandler handler = new TraceHandler(animal);

		// 获得真实对象(animal)的一个代理类 ★★★★★
		Object proxyObj = Proxy.newProxyInstance(animal.getClass()
				.getClassLoader(), // 真实对象的类加载器
				animal.getClass().getInterfaces(), // 真实对象实现的所有接口
				handler // 真实对象的处理器
				);

		if (proxyObj instanceof IAnimal) {
			System.out.println("the proxyObj is an animal!");

			IAnimal animalProxy = (IAnimal) proxyObj;// proxyObj与animal都实现了IAnimal接口

			animalProxy.info();// 像普通animal对象一样使用(通过handler的invoke方法执行)
			
			animalProxy.otherInfo();
		} else {
			System.out.println("the proxyObj isn't an animal!");
		}
	}
}
