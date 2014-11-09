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
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;

import com.beans.testBean.Complex;
import com.beans.testBean.Person;
import com.beans.testBean.PersonEntity;


/**

BeanUtils.copyProperties 与 PropertyUtils.copyProperties 都是拷贝对象属性的方法，
BeanUtils 支持类型转换，而 PropertyUtils 不支持。
但是 BeanUtils 不允许对象的属性值为 null，PropertyUtils 可以拷贝属性值 null 的对象。
如果对象属性值为 null，
BeanUtils.copyProperties 方法会报 commons.beanutils.ConversionException: No value specified 错误。

使用BeanUtils的成本惊人地昂贵！
BeanUtils所花费的时间要超过取数据、将其复制到对应的 value对象（通过手动调用get和set方法），
 */
public class PropertyTest {
	private static Complex bean = new Complex();
	static {
		bean.setArray(new int[] { 1, 2, 4, 7 });
		bean.setId("zhai");

		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		bean.setList(list);

		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		map.put("k", "val");
		bean.setMap(map);

		Person myBean = new Person();
		myBean.setId(2);
		myBean.setName("ben");
		bean.setMyBean(myBean);

		List<Person> personList = new ArrayList<Person>();
		personList.add(new Person(1, "per1", 12, null, null, null));
		personList.add(new Person(1, "per23", 22, null, null, null));
		bean.setPersonList(personList);

	}

	@Test
	public void beanUtilsGetTest() {
		Map<String, Object> properties = null;
		try {
			properties = BeanUtils.describe(bean);
			Object obj = properties.get("personList");
			System.out.println("className:" + obj.getClass().getName()
					+ " value:" + obj);

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
	public void propertyUtilsGetTest() {
		Object object = null;
		try {
			object = PropertyUtils.getProperty(bean, "personList");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (object instanceof List) {
			System.out.println("list");
		}
	}

}
