package org.slieer.guava;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

public class GuavaStringTest {
	static Logger logger = LogManager.getLogger(GuavaStringTest.class.getName());	
	
	@Test
	public void testStrings(){
		String str = "abc";
		
		//String str = null;
		Optional<String> str1 = Optional.of(str);
		
		logger.info("str: {}",  str1.get());
		
		logger.info("str is not null :{}", str1.isPresent());
		
		logger.info("is nul or empty : {}" ,Strings.isNullOrEmpty(str));
		logger.info("strings prefix test: {}",Strings.commonPrefix(str, "ab"));
		logger.info("3 times str: {}", Strings.repeat(str, 3));
		
	}
}
