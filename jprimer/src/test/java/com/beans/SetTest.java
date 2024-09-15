package com.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.beans.testBean.FamilyList;

public class SetTest {
	public static void main(String[] args) {
		testSet();
	}
	
	public static void testSet(){
		FamilyList bean = new FamilyList();
		List<String> list = new ArrayList<String>(){
			private static final long serialVersionUID = 5821577312402357995L;
			private List<String> setValue(){
				List<String> temp = new ArrayList<String>();
				temp.add("aa");
				temp.add("bb");
				return temp;
			}
		}.setValue();
		
		bean.setHomeSet(list);
		
		try {
			//BeanUtils.getIndexedProperty(bean, "tempSet",1);
			PropertyUtils.getIndexedProperty(bean, "tempSet");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}


