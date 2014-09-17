package com.slieer.commons.Digester.student;

public class Student {
	private String name;
	private String course;

	@Override
	public String toString() {
		return "Student [name=" + name + ", course=" + course + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

}
