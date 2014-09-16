package com.slieer.commons.BeanUtils.userguide.bean;

public class Address {
	private String country;
	private String province;
	private String county;
	private String villagesAndTowns;

	public Address() {
		super();
	}

	public Address(String country, String province, String county,
			String villagesAndTowns) {
		super();
		this.country = country;
		this.province = province;
		this.county = county;
		this.villagesAndTowns = villagesAndTowns;
	}

	public enum AddrType {
		HOME, COMPANY, OTHER
	}
	
	public static Address getDefaultAddr(){
		Address adr = new Address("china", "shanxi", "yangxian", "HJYLSY");
		return adr;
	}
	
	@Override
	public String toString() {
		return "Address [country=" + country + ", province=" + province
				+ ", county=" + county + ", villagesAndTowns="
				+ villagesAndTowns + "]";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getVillagesAndTowns() {
		return villagesAndTowns;
	}

	public void setVillagesAndTowns(String villagesAndTowns) {
		this.villagesAndTowns = villagesAndTowns;
	}

}