package com.slieer.spring.hello;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.slieer.spring.hello.service.ManufactureService;
import com.slieer.spring.hello.service.MessageService;

@Configuration
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

	@Bean
	ManufactureService mockProduce() {
		return new ManufactureService() {

			@Override
			public String produce(Fruit f) {
				return "produce " + f.name();
			}

		};
	}
	
	@PostConstruct
	public void init(){
		logger.info("init ...");
	}
	
	@PreDestroy
	public void destroy(){
		logger.info("destroy ...");
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