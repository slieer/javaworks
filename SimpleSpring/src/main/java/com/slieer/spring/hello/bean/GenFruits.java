package com.slieer.spring.hello.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.slieer.spring.hello.Application;
import com.slieer.spring.hello.service.ManufactureService;
import com.slieer.spring.hello.service.MessageService;

@Configuration
//@ImportResource
@ComponentScan
public class GenFruits {
	private static Logger logger = LogManager.getLogger(Application.class.getName());

	@Bean
	MessageService mockMessageService() {
		return new MessageService() {
			public String getMessage() {
				return "Hello World!";
			}
		};
	}

	@Bean(name = "msgImpl", initMethod = "init", destroyMethod = "destroy")
	ManufactureService mockProduce() {
		return new ManufactureService() {

			@SuppressWarnings("unused")
			public void init() {
				logger.info("init bean test...");
			}

			@Override
			public String produce(Fruit f) {
				return "produce " + f.name();
			}

			@SuppressWarnings("unused")
			public void destroy() {
				logger.info("destroy bean test...");
			}

		};
	}

	
}
