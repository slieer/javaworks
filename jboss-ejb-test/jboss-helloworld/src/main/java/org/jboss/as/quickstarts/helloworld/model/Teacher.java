package org.jboss.as.quickstarts.helloworld.model;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable{
	private static final long serialVersionUID = -2666176308004777409L;
	
	Integer id;
	String name;
	
	List<Student> students;
}
