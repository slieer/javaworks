package com.slieer.spring.hello.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.slieer.spring.hello.service.UserBean;

@Configuration
//@ImportResource
@ComponentScan
public class GenPersons {
	@Bean
	public UserBean user() {
		UserBean user = new UserBean();
		user.setId(1);
		user.setUserName("china");
		return user;
	}
}
