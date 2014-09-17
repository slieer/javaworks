package com.slieer.commons.Digester.student;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.junit.Test;

public class StudentTest {
	private Vector<Student> students= new Vector<Student>(5);
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	@Override
	public String toString() {
		return "StudentTest [students=" + students + "]";
	}

	@Test
	public void test() {
		//List<Student> students = ;
		// 创建实例
		Digester digester = new Digester();
		// 将初始对象压入 digester 的 stack
		digester.push(new ArrayList<Student>(5));
		digester.addObjectCreate("students/student", Student.class);
		// 设置对象属性
		digester.addBeanPropertySetter("students/student/name");
		digester.addBeanPropertySetter("students/student/course");
		// 当移动到下一个标签中时的动作
		digester.addSetNext("students/student", "add");

		try {
			InputStream in = StudentTest.class
					.getResourceAsStream("students.xml");
			// 解析
			List<Student> ds = (List<Student>) digester.parse(in);
			System.out.print("firstMethod:" + ds);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	@Test
	public void test1() {
		// 创建实例
		Digester digester = new Digester();
		// 将初始对象压入 digester 的 stack
		digester.push(new StudentTest());
		// 指明匹配模式和要创建的类
		digester.addObjectCreate("students/student", Student.class);
		// 设置对象属性
		digester.addBeanPropertySetter("students/student/name");
		digester.addBeanPropertySetter("students/student/course");
		// 当移动到下一个标签中时的动作
		digester.addSetNext("students/student", "addStudent");

		try {
			InputStream in = StudentTest.class
					.getResourceAsStream("students.xml");
			// 解析
			StudentTest ds = (StudentTest) digester.parse(in);
			System.out.print(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
