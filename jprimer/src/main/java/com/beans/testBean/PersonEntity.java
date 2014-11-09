package com.beans.testBean;

import java.util.Date;

public class PersonEntity {
	private int id;
	private String name;
	private int age;
	private Long salary;
	private Date date;
	
	private FamilyListEntity families;	
	
	public PersonEntity() {
		super();
	}
	public PersonEntity(int id, String name, int age, Long salary, Date date,
			FamilyListEntity families) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.date = date;
		this.families = families;
	}
	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", name=" + name + ", age=" + age
				+ ", salary=" + salary + ", date=" + date + ", families="
				+ families + "]";
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
	public FamilyListEntity getFamilies() {
		return families;
	}
	public void setFamilies(FamilyListEntity families) {
		this.families = families;
	}
}
