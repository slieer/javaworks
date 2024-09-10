package org.slieer.commons.BeanUtils.userguide.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slieer.commons.BeanUtils.userguide.bean.Address.AddrType;


public class Student {
	private String name;
	private int age;
	private Date birth;

	private Address defaultAddr;
	private List<Address> addrList;

	private Map<AddrType, Address> addrMap;

	
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", birth=" + birth
				+ ", defaultAddr=" + defaultAddr + ", addrList=" + addrList
				+ ", addrMap=" + addrMap + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Address getDefaultAddr() {
		return defaultAddr;
	}

	public void setDefaultAddr(Address defaultAddr) {
		this.defaultAddr = defaultAddr;
	}

	public List<Address> getAddrList() {
		return addrList;
	}

	public void setAddrList(List<Address> addrList) {
		this.addrList = addrList;
	}

	public Map<AddrType, Address> getAddrMap() {
		return addrMap;
	}

	public void setAddrMap(Map<AddrType, Address> addrMap) {
		this.addrMap = addrMap;
	}

	
};
