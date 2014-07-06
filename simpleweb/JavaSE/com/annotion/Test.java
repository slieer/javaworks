package com.annotion;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.annotion.simple.DescriptionAnnotion;
import com.annotion.simple.KeyValueAnnotation;
import com.annotion.simple.NameAnnotinon;
import com.annotion.simple.NameBean;

public class Test {
	@org.junit.Test
	public void testSimple() throws IOException, ClassNotFoundException,
			SecurityException, NoSuchMethodException {
		Class<?> c = Class.forName("com.annotion.simple.KeyValueBean");
		Method saveMethod = c.getMethod("save");
		Annotation an[] = saveMethod.getAnnotations(); // 取得全部的运行时Annotation
		for (Annotation a : an) {
			System.out.println(a);
		}

		if (saveMethod.isAnnotationPresent(KeyValueAnnotation.class)) // 该方法上是否存在该种类型的注解
		{
			KeyValueAnnotation ma = saveMethod
					.getAnnotation(KeyValueAnnotation.class);
			String key = ma.key();
			String value = ma.value();
			System.out.println("key = " + key);
			System.out.println("value = " + value);
		}
	}
	
	@org.junit.Test
	public void testName() throws Exception {
		//String CLASS_NAME = "com.annotion.simple.NameBean";
		//Class<NameBean> test = (Class<NameBean>)Class.forName(CLASS_NAME);
		
		Class<NameBean> test = NameBean.class;
		Method[] method = test.getMethods();
		boolean flag = test.isAnnotationPresent(DescriptionAnnotion.class);
		if (flag) {
			DescriptionAnnotion des = (DescriptionAnnotion) test
					.getAnnotation(DescriptionAnnotion.class);
			System.out.println("描述:" + des.value());
			System.out.println("-----------------");
		}

		// 把JavaEyer这一类有利用到@Name的全部方法保存到Set中去
		Set<Method> set = new HashSet<Method>();
		for (int i = 0; i < method.length; i++) {
			boolean otherFlag = method[i]
					.isAnnotationPresent(NameAnnotinon.class);
			if (otherFlag)
				set.add(method[i]);
		}
		for (Method m : set) {
			NameAnnotinon name = m.getAnnotation(NameAnnotinon.class);
			System.out.println(name.originate());
			System.out.println("创建的社区:" + name.community());
		}
	}
}