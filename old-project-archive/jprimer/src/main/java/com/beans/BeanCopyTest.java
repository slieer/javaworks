package com.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;

import com.beans.testBean.Person;
import com.beans.testBean.PersonEntity;

public class BeanCopyTest {
	/**
	 * 註冊轉化器到全局
	 * 对象间的复制
	 * 
	 * @param args
	 */
	@Test
	public void copy() {
		Person bean = constructBean();

		PersonEntity en = new PersonEntity();
		try {
			ConvertUtils.register(new DateConverter(), Date.class);
			ConvertUtils.register(new LongConverter(), Long.class);

			BeanUtils.setProperty(en, "salary", null);
			BeanUtils.copyProperties(en,bean);

			System.out.println(en);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void copy1() throws Exception {
		Person bean = constructBean();
		PersonEntity entity = new PersonEntity();
		
		new PropertyUtilsBean().copyProperties(entity, bean);
		System.out.println(entity);
		
//		ConvertUtilsBean convertBean = new ConvertUtilsBean();
//		entity = new PersonEntity();
//		new BeanUtilsBean(convertBean).copyProperties(entity, bean);;		
//		System.out.println(entity);
	}
		
	private Person constructBean() {
		Person bean = new Person();
		bean.setAge(1);
		bean.setId(1);
		bean.setName("abc");
		return bean;
	}

	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	class DateConverter implements Converter {
		public Object convert(Class arg0, Object arg1) {
			Date curr = (Date) arg1;
			if (arg1 == null) {
				curr = new Date();
			}
			return format.format(curr);
		}
	}

	class LongConverter implements Converter {

		public Object convert(Class arg0, Object arg1) {
			return arg1 == null ? 0L : arg1;
		}

	}	
}
