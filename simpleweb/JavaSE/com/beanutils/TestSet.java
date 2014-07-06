package com.beanutils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class TestSet {
	public static void main(String[] args) {
		testSet();
	}
	
	public static void testSet(){
		MySetBean bean = new MySetBean();
		List<String> list = new ArrayList<String>(){
			private static final long serialVersionUID = 5821577312402357995L;
			private List<String> setValue(){
				List<String> temp = new ArrayList<String>();
				temp.add("aa");
				temp.add("bb");
				return temp;
			}
		}.setValue();
		bean.setTempSet(list);
		
		try {
			//BeanUtils.getIndexedProperty(bean, "tempSet",1);
			PropertyUtils.getIndexedProperty(bean, "tempSet");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}

class MySetBean{
	private List<String> tempSet = new ArrayList<String>();

	public List<String> getTempSet() {
		return tempSet;
	}

	public void setTempSet(List<String> tempSet) {
		this.tempSet = tempSet;
	}
}

