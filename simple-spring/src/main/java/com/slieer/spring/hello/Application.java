package com.slieer.spring.hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.slieer.spring.hello.bean.GenFruits;
import com.slieer.spring.hello.bean.GenPersons;
import com.slieer.spring.hello.component.MessagePrinter;

/**
 * @author dev
 *
 *Spring 对标注 Configuration 的类有如下要求： 
	配置类不能是 final 的；配置类不能是本地化的，亦即不能将配置类定义在其他类的方法内部；配置类必须有一个无参构造函数。
	AnnotationConfigApplicationContext 将配置类中标注了 @Bean 的方法的返回值识别为 Spring Bean，并注册到容器中，受 IoC 容器管理。
 *
 */

public class Application {
	private static Logger logger = LogManager.getLogger(Application.class.getName());
	
	/**方法一*/
	@Test
	public void testContext1() {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				"com.slieer.spring.hello");
		MessagePrinter printer = ctx.getBean(MessagePrinter.class);
		printer.printMessage();
		
		ctx.close();
		//int i = ; // xor 7
		logger.info("testContext1 4 ^ 3=" + (4 ^ 3));
	}
	
	/**方法二*/
	@Test
	public void testContext2() {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				GenFruits.class, GenPersons.class, MessagePrinter.class);
		MessagePrinter printer = ctx.getBean(MessagePrinter.class);
		printer.printMessage();
		
		ctx.close();
		//int i = ; // xor 7
		logger.info("testContext2 4 ^ 3=" + (4 ^ 3));
	}
	
	/**方法三*/
	@Test
	public void testContext3() {
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				Config.class);
		MessagePrinter printer = ctx.getBean(MessagePrinter.class);
		printer.printMessage();
		
		ctx.close();
	}	
}