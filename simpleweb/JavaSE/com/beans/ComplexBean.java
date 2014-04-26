package com.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ComplexBean {
	private String id;
	
	private Integer integer;
	private Date date;
	private Long longVal;
	
	private int[] array;
	private List<String> list = new ArrayList<String>();
	private Map<String,String> map = new HashMap<String,String>();
	private ComplexBean nested;
	private List<PersonBean> personList = new ArrayList<PersonBean>();
	
	private MyBean myBean;

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String,String> getMap() {
		return map;
	}

	public void setMap(Map<String,String> map) {
		this.map = map;
	}

	public ComplexBean getNested() {
		return nested;
	}

	public void setNested(ComplexBean nested) {
		this.nested = nested;
	}

	public MyBean getMyBean() {
		return myBean;
	}

	public void setMyBean(MyBean myBean) {
		this.myBean = myBean;
	}

	public List<PersonBean> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PersonBean> personList) {
		this.personList = personList;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
	
	public Long getLongVal() {
		return longVal;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	@Override
	public String toString() {
		return "ComplexBean [id=" + id + ", integer=" + integer + ", date="
				+ date + ", longVal=" + longVal + ", array="
				+ Arrays.toString(array) + ", list=" + list + ", map=" + map
				+ ", nested=" + nested + ", personList=" + personList
				+ ", myBean=" + myBean + "]";
	}
}
