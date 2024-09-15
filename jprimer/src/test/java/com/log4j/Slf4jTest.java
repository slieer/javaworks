package com.log4j;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	 
	private static final Logger log = LoggerFactory.getLogger(Slf4jTest.class);
	
	@Test
	public void f(){
		log.info("hello slf4j2!");
	}
}
