package com.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

public class PropertyTest {
	private static ComplexBean bean = new ComplexBean();
	static{
		bean.setArray(new int[]{1,2,4,7});
		bean.setId("zhai");
		
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		bean.setList(list);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		map.put("k", "val");
		bean.setMap(map);
		
		MyBean myBean = new MyBean();
		myBean.setId("myben");
		myBean.setName("ben");
		bean.setMyBean(myBean);
		
		List<PersonBean> personList = new ArrayList<PersonBean>();
		personList.add(new PersonBean(1, "per1", 12, null, null));
		personList.add(new PersonBean(1, "per23", 22, null, null));
		bean.setPersonList(personList);
		
	}
	
	@Test
	public void beanUtilsGetTest(){
		Map<String, Object> properties = new HashMap<String, Object>();
		try {
			properties = BeanUtils.describe(bean);
			Object obj = properties.get("personList");
			System.out.println("className:" + obj.getClass().getName() + " value:" + obj);
			
			obj = properties.get("map");
			System.out.println("map value:" + obj);
			
			obj = properties.get("integer");
			System.out.println("integer value:" + obj);
						
			Object integer = BeanUtils.getProperty(bean, "integer");
			System.out.println(integer);
						
			Object date = BeanUtils.getProperty(bean, "date");
			System.out.println(date);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void propertyUtilsGetTest(){
		Object object = null;
		try {
			object = PropertyUtils.getProperty(bean, "personList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(object instanceof List){
			System.out.println("list");
		}
	}
	
	/**
	 * 对象间的复制
	 * @param args
	 */
	@Test
	public void copy() {
		
		PersonBean bean = new PersonBean();
		bean.setAge(1);
		bean.setId(1);
		bean.setName("abc");
		
		PersonEntity en = new PersonEntity();
		try {
			//ConvertUtils.register(new DateConverter(), Date.class);
			ConvertUtils.register(new LongConverter(), Long.class);
			
			BeanUtils.setProperty(en, "salary", null);
			//BeanUtils.copyProperties(en,bean);
			PropertyUtils.copyProperties(en,bean);
			
			System.out.println(en);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class DateConverter implements Converter{
			@Override
			public Object convert(Class arg0, Object arg1) {
				Date curr = (Date)arg1;
				if(arg1 == null){
					curr = new Date();
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				return format.format(curr);
		}		
	}
	
	class LongConverter implements Converter{

		@Override
		public Object convert(Class arg0, Object arg1) {
			return arg1 == null ? 0L : arg1;
		}
		
	}
}
