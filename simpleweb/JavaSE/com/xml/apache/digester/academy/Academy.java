package com.xml.apache.digester.academy;

import java.util.Vector;

public class Academy {
	private String name;
	private Vector<Student> students;
	private Vector<Teacher> teachers;

	{
		this.setStudents(new Vector<Student>());
		this.setTeachers(new Vector<Teacher>());
	}
	
	public void addStudent(Student student){
		this.getStudents().add(student);
	}
	
	public void addTeacher(Teacher teacher){
		this.getTeachers().add(teacher);
	}	
	
	@Override
	public String toString() {
		return "Academy [name=" + name + ", students=" + students
				+ ", teachers=" + teachers + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Student> getStudents() {
		return students;
	}

	public void setStudents(Vector<Student> students) {
		this.students = students;
	}

	public Vector<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Vector<Teacher> teachers) {
		this.teachers = teachers;
	}
}