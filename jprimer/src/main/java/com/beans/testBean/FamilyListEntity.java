package com.beans.testBean;

import java.util.ArrayList;
import java.util.List;

public class FamilyListEntity {
	private Integer cerNum;
	private List<String> homeSet = new ArrayList<String>();

	@Override
	public String toString() {
		return "FamilyListEntity [cerNum=" + cerNum + ", homeSet=" + homeSet + "]";
	}

	public Integer getCerNum() {
		return cerNum;
	}

	public void setCerNum(Integer cerNum) {
		this.cerNum = cerNum;
	}

	public List<String> getHomeSet() {
		return homeSet;
	}

	public void setHomeSet(List<String> homeSet) {
		this.homeSet = homeSet;
	}
}

