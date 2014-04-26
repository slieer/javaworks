package com.xml.apache.digester.academy.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.binder.RulesModule;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.xml.apache.digester.academy.Academy;
import com.xml.apache.digester.academy.Course;
import com.xml.apache.digester.academy.Student;
import com.xml.apache.digester.academy.Teacher;

public class Academyparser {
	private String aca = "JavaSE/com/xml/apache/digester/xml.academy.xml";
	private File xmlFile = new File(aca);
	private String file = "JavaSE/com/xml/apache/digester/xml.academy.rule.xml";
	
	@Test
	public void digestXmlRules(){
		FileReader characterStream = null;
		try {
			characterStream = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputSource rulesSource = new InputSource(characterStream);
		Digester digester = new Digester();
		digester.setValidating(false);
		//digester.
		
		//URL url= this.getClass().getClassLoader().getResource("xml.academy.xml");
		try {
			Academy aca = (Academy)digester.parse(xmlFile);
			System.out.println(aca);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	// <certification>在程序中对应一个字符串，故而没有列出。
	public void digest() {
		Digester digester = new Digester();
		// 注意，此处并没有象上例一样使用push，是因为此处从根元素创建了一个对//象实例
		digester.addObjectCreate("academy", Academy.class);
		// 将< academy >的属性与对象的属性关联
		digester.addSetProperties("academy");
		digester.addObjectCreate("academy/student", Student.class);
		digester.addSetProperties("academy/student");
		digester.addObjectCreate("academy/student/course", Course.class);
		digester.addBeanPropertySetter("academy/student/course/id");
		digester.addBeanPropertySetter("academy/student/course/name");
		digester.addSetNext("academy/student/course", "addCourse");
		digester.addSetNext("academy/student", "addStudent");
		digester.addObjectCreate("academy/teacher", Teacher.class);
		digester.addSetProperties("academy/teacher");
		// 当遇到academy/teacher/certification时，调用addCertification
		digester.addCallMethod("academy/teacher/certification",
				"addCertification", 1);
		// 设置addCertification的参数值，此处的0表示这个元素体的第一个值
		// 为参数值传入addCertification。在此处，即为<certification>的值。
		// （因为就只有一个）
		digester.addCallParam("academy/teacher/certification", 0);
		digester.addSetNext("academy/teacher", "addTeacher");
		try {
			Academy a = (Academy) digester.parse(xmlFile);
			System.out.print(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}