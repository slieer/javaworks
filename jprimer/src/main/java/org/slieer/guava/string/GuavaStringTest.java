package org.slieer.guava.string;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

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
	
	@Test
	public void splitterTest(){
		Iterable<String> it = Splitter.on(" ").split("1 2 3");
		logger.info(it);
		
		Splitter.onPattern("\\s+").split("1 \t   2 3");//["1", "2", "3"]
		//Splitter 还支持根据长度来拆分字符串。
		Splitter.fixedLength(3).split("1 2 3");//["1 2", " 3"]
		
		Splitter.on("#").withKeyValueSeparator(":").split("1:2#3:4");//{"1":"2", "3":"4"}
		
	}
}
