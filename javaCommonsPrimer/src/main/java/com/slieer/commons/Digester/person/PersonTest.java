package com.slieer.commons.Digester.person;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.junit.Test;

public class PersonTest {
	@Test
	public void test() throws Exception {
		// 从XML规则集中配置Digester
		URL rules = getClass().getResource("person-rules.xml");
		Digester digester = DigesterLoader.createDigester(rules);// 将空的List推入到Digester的堆栈List
		List<Person> people = new ArrayList<Person>();
		digester.push(people);//
		// 解析XML文档
		// InputStream input = new FileInputStream( );
		InputStream input = getClass().getResourceAsStream("person.xml");
		digester.parse(input);

		System.out.println(people);
	}

	@Test
	public void test1() throws Exception {
		//digester.setClassLoader(Thread.currentThread().getContextClassLoader());  
		
		Digester digester = new Digester();
		List<Person> people = new ArrayList<Person>();
		digester.push(people);//		
		digester.setValidating(false);
		
		digester.addObjectCreate("people/person", Person.class);

		digester.addBeanPropertySetter("people/person/name");
		digester.addBeanPropertySetter("people/person/age");
		digester.addSetNext("people/person", "add", Person.class.getName());
		
		
		InputStream input = getClass().getResourceAsStream("person.xml");
		Object obj = digester.parse(input);
		System.out.println("people:" + people);
		//System.out.println("people:" + obj);
		
	}
}
