package com.xml.apache.digester.academy;

public class Course {
	private String id;
	private String name;

	
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}