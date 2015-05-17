package com.slieer.spring.hello.service;

public interface ManufactureService {
	enum Fruit{
		APPLE, 香蕉
	};
	
	String produce(Fruit f);
}
