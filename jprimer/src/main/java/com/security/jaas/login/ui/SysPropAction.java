package com.security.jaas.login.ui;

import java.security.PrivilegedAction;

@SuppressWarnings("unchecked")
public class SysPropAction implements PrivilegedAction{
	
	public SysPropAction(String propertyName) {
		super();
		this.propertyName = propertyName;
	}

	@Override
	public Object run() {
		return System.getProperty(propertyName);
	}
	
	private String propertyName;
}
