package com.slieer.spring.hello.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BeanConfig {
	public class User {
		int id;
		String userName;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

	}

	@Bean
	public User user() {
		User user = new User();
		user.setId(1);
		user.setUserName("china");
		return user;
	}
}
