package com.xml.apache.digester.academy;

import java.util.Vector;

public class Student {
	private String name;
	private String division;
	private Vector<Course> courses;

	public void addCourse(Course course){
		Vector<Course> courses = this.getCourses();
		if(courses == null){
			courses = new Vector<Course>();
			this.setCourses(courses);
		}
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

	public Vector<Course> getCourses() {
		return courses;
	}

	public void setCourses(Vector<Course> courses) {
		this.courses = courses;
	}
}
