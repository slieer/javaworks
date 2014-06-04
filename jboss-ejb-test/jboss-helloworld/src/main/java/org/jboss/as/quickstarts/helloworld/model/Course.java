package org.jboss.as.quickstarts.helloworld.model;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable{
	private static final long serialVersionUID = -298929281828732947L;

	private Integer id;
	private String name;
	private int credit;
	
	
	List<Student> students;
	List<Teacher> teachers;

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", students=" + students
				+ ", teachers=" + teachers + "]";
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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
}
