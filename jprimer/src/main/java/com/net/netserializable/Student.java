package com.net.netserializable;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = -4239750260379324633L;

	private int sno;
	private String sname;
	private transient String spouseName;

	public static void main(String[] args) {
		System.out.println("ABC");
	}
	
	public Student(int sno, String sname, String spouseName) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.spouseName = spouseName;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result + sno;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (sname == null) {
			if (other.sname != null)
				return false;
		} else if (!sname.equals(other.sname))
			return false;
		if (sno != other.sno)
			return false;
		return true;
	}

}
