package org.slieer.commons.BeanUtils.userguide.bean;

public class AddressPO {
	private String country;
	private String province;
	private String county;
	private String villagesAndTowns;

	@Override
	public String toString() {
		return "AddressPO [country=" + country + ", province=" + province
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
