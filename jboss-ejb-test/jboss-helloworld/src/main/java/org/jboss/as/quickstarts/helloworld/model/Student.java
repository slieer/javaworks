package org.jboss.as.quickstarts.helloworld.model;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable{
	private static final long serialVersionUID = -4759114292267662466L;

	private Integer id;
	private String name;

	private List<Teacher> teachers;

	private List<Course> books;

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", teachers="
				+ teachers + ", books=" + books + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Course> getBooks() {
		return books;
	}

	public void setBooks(List<Course> books) {
		this.books = books;
	}

}
