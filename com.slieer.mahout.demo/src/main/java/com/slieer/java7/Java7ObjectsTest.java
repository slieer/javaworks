package com.slieer.java7;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Java7ObjectsTest {
	private static Logger logger = LogManager.getLogger(Java7ObjectsTest.class
			.getName());

	@Test
	public void test() {
		logger.info("test hash {}", Objects.hash("a", "ab", "abc"));
		logger.info("test  {}", Objects.requireNonNull(null));
	}
}
