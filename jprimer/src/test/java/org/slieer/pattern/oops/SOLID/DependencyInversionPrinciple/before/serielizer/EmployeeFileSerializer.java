package org.slieer.pattern.oops.SOLID.DependencyInversionPrinciple.before.serielizer;

import org.slieer.pattern.oops.SOLID.DependencyInversionPrinciple.before.employees.Employee;
import org.slieer.pattern.oops.SOLID.DependencyInversionPrinciple.before.employees.Employee;

public class EmployeeFileSerializer {
    public String serialize(Employee employee) {
        StringBuilder sb = new StringBuilder();

        sb.append("### EMPLOYEE RECORD ####");
        sb.append(System.lineSeparator());
        sb.append("NAME: ");
        sb.append(employee.getFullName());
        sb.append(System.lineSeparator());
        sb.append("POSITION: ");
        sb.append(employee.getClass().getTypeName());
        sb.append(System.lineSeparator());
        sb.append("EMAIL: ");
        sb.append(employee.getEmail());
        sb.append(System.lineSeparator());
        sb.append("MONTHLY WAGE: ");
        sb.append(employee.getMonthlyIncome());
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
