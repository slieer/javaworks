package org.slieer.pattern.oops.SOLID.openClosePrinciple.before.persistence;

import java.util.Arrays;
import java.util.List;

import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.Employee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.FullTimeEmployee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.PartTimeEmployee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.Employee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.FullTimeEmployee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.PartTimeEmployee;

public class EmployeeRepository {

    public List<Employee> findAll(){

        // Employees are kept in memory for simplicity
        Employee anna = new FullTimeEmployee("Anna Smith", 2000);
        Employee billy = new FullTimeEmployee("Billy Leech", 920);

        Employee steve = new PartTimeEmployee("Steve Jones", 800);
        Employee magda = new PartTimeEmployee("Magda Iovan", 920);

        return Arrays.asList(anna, billy, steve, magda);
    }
}