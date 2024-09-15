package org.slieer.commons.Digester.student.bean;

import java.util.Vector;

public class Academy {
	private String name;
	private Vector<Student> students = new Vector<Student>();
	private Vector<Teacher> teachers = new Vector<Teacher>();

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void addStudent(Student student) {
		students.add(student);
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