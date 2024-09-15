package com.beans.testBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	private int id;
	private String name;
	private int age;
	private Long salary;
	private Date date;

	private FamilyList families;
}
