package org.slieer.pattern.oops.SOLID.singleResponsibilityPrinciple.before;

public class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String fullName, int monthlyIncome) {
        super(fullName, monthlyIncome);
        this.setNbHoursPerWeek(20);
    }
}