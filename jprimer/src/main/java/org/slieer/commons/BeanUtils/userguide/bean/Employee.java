package org.slieer.commons.BeanUtils.userguide.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slieer.commons.BeanUtils.userguide.bean.Address.AddrType;


public class Employee {

	private String firstName;
	private String lastName;
	private List<Employee> subordinate = new ArrayList<Employee>();

	private Map<AddrType, Address> address = new HashMap<AddrType, Address>();

	
	public List<Employee> getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(List<Employee> subordinate) {
		this.subordinate = subordinate;
	}

	public Map<AddrType, Address> getAddress() {
		return address;
	}

	public void setAddress(Map<AddrType, Address> address) {
		this.address = address;
	}



	public Address getAddress(String type) {
		AddrType t = AddrType.valueOf(type);
		return this.address.get(t);
	}

	public void setAddress(String type, Address address) {
		AddrType t = AddrType.valueOf(type);
		this.address.put(t, address);
	}

	public Employee getSubordinate(int index) {
		return subordinate.get(index);
	}

	public void setSubordinate(int index, Employee subordinate) {
		this.subordinate.add(index, subordinate);
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;

	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}