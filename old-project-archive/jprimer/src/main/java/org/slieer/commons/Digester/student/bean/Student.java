package org.slieer.commons.Digester.student.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Student {
	private String name;
	private String division;
	private List<Course> courses = new ArrayList<Course>();

	public void addCourse(Course course){
		courses.add(course);
	}
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", division=" + division
				+ ", courses=" + courses + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}