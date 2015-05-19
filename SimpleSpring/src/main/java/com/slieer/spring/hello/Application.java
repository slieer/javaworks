package com.slieer.spring.hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.slieer.spring.hello.service.ManufactureService;
import com.slieer.spring.hello.service.MessageService;

/**
 * @author dev
 *
 *Spring 对标注 Configuration 的类有如下要求： 
	配置类不能是 final 的；配置类不能是本地化的，亦即不能将配置类定义在其他类的方法内部；配置类必须有一个无参构造函数。
	AnnotationConfigApplicationContext 将配置类中标注了 @Bean 的方法的返回值识别为 Spring Bean，并注册到容器中，受 IoC 容器管理。
 *
 */

@Configuration
//@ImportResource
@ComponentScan
public class Application {
	private static Logger logger = LogManager.getLogger(Application.class.getName());

	@Bean
	MessageService mockMessageService() {
		return new MessageService() {
			public String getMessage() {
				return "Hello World!";
			}
		};
	}

	@Bean(name="msgImpl", initMethod="init", destroyMethod="destroy")
	ManufactureService mockProduce() {
		return new ManufactureService() {
			
			@SuppressWarnings("unused")
			public void init(){
				logger.info("init bean test...");
			}
			
			@Override
			public String produce(Fruit f) {
				return "produce " + f.name();
			}
			
			@SuppressWarnings("unused")
			public void destroy(){
				logger.info("destroy bean test...");
			}

		};
	}
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(
				Application.class);
		MessagePrinter printer = context.getBean(MessagePrinter.class);
		printer.printMessage();

		context.close();

		//int i = ; // xor 7
		logger.info("4 ^ 3=" + (4 ^ 3));
	}
}