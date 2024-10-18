package org.slieer.pattern.oops.designPatterns.adapter;


import java.util.List;

import org.slieer.pattern.oops.designPatterns.adapter.employees.Employee;

public class AdapterDemo {

	public static void main(String[] args) {
		EmployeeClient client = new EmployeeClient();
		
		List<Employee> employees = client.getEmployeeList();
		
		System.out.println(employees);
	}
}
