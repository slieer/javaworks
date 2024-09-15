package org.slieer.xml.apache.digester.academy;

import lombok.Data;

import java.util.Vector;

@Data
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
}