package org.slieer.commons.BeanUtils.userguide;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.slieer.commons.BeanUtils.userguide.bean.Address;
import org.slieer.commons.BeanUtils.userguide.bean.Address.AddrType;
import org.slieer.commons.BeanUtils.userguide.bean.Employee;


public class Propertyest {
	Employee employee = new Employee();
	{
		employee.setFirstName("slieer");
		employee.setLastName("yu");

		Employee subordinate = new Employee();
		employee.setSubordinate(0, subordinate);

		Address home = new Address();
		Address company = new Address();
		employee.setAddress(AddrType.HOME.toString(), home);
		employee.setAddress(AddrType.COMPANY.toString(), company);
	}

	@Test
	public void testBasicProperty() throws Exception {
		String firstName = (String) PropertyUtils.getSimpleProperty(employee,
				"firstName");
		String lastName = (String) PropertyUtils.getSimpleProperty(employee,
				"lastName");

		PropertyUtils.setSimpleProperty(employee, "firstName", firstName);
		PropertyUtils.setSimpleProperty(employee, "lastName", lastName);
	}

	@Test
	public void testIndexedProperty() throws Exception {
		int index = 0;
		String name = "subordinate[" + index + "]";
		Employee subordinate = (Employee) PropertyUtils.getIndexedProperty(
				employee, name);

		index = 0;
		Employee subordinate1 = (Employee) PropertyUtils.getIndexedProperty(
				employee, "subordinate", index);
	}

	@Test
	public void testMappedProperty() throws Exception {
		// Employee employee = ...;
		Address address = new Address();
		PropertyUtils.setMappedProperty(employee, "address(home)", address);

		// Employee employee = ...;
		address = new Address();
		PropertyUtils.setMappedProperty(employee, "address", "home", address);
	}

	@Test
	public void testNestedProperty()  throws Exception {
		String city = (String) PropertyUtils.getNestedProperty(employee,
				"address(home).city");

		city = (String) PropertyUtils.getProperty(employee,
				"subordinate[3].address(home).city");
	}
}
