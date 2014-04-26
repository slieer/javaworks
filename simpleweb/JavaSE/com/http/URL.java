package com.http;

import java.text.MessageFormat;

import org.junit.Test;


public class URL {
	@Test
	public void urlEncodeTest() {
		String url = "+8615815562175";
		//
		String encodeUrl = url.replace("+", "%2B");
		System.out.println(encodeUrl);
		
		System.out.println(" ".replace(" ", "%20"));	
	}
	
	@Test
	public void format(){
		String method =  "+8615815562175";
		String nodeSelector = "/~~/contacts/method={0}";
			
		MessageFormat form = new MessageFormat(nodeSelector);
		Object[] args = { method};
		String formated = form.format(args);
		System.out.println(formated);
	}
}
