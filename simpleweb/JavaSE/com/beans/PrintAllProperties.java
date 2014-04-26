package com.beans;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;

public class PrintAllProperties {
	public static void main(String... args){
		PersonBean p = new PersonBean();
		p.setAge(1);
		p.setId(1);
		p.setName("n");
		
		//serializableBeanToString(p.getClass());
		try {
			f(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static String serializableBeanToString(Class clazz) {
		String s = "\r\n//==========formBean=================\r\n";
		try {
			Field afield[] = clazz.getDeclaredFields();
			for (int i = 0; i < afield.length; i++)
				s = s + afield[i].getName() + ":" + afield[i].get(afield[i].getName())
						+ "\r\n";
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		s = s + "============formBean============\r\n";
		return s;
	}
	
	static void f(Object obj) throws Exception{
		Class clazz = obj.getClass();
		Field afield[] = clazz.getDeclaredFields();
		for(int i = 0; i < afield.length; i++){
			String val = BeanUtils.getIndexedProperty(obj, afield[i].getName());
			System.out.print(val);
		}
	}
}
