package com.beans;

import java.util.Date;

public class PersonBean {
	private int id;
	private String name;
	private int age;
	private Long salary;
	private Date date;	
	
	public PersonBean() {
		super();
	}

	public PersonBean(int id, String name, int age, Long salary, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "PersonBean [id=" + id + ", name=" + name + ", age=" + age
				+ ", salary=" + salary + ", date=" + date + "]";
	}

}
