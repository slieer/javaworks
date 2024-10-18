package org.slieer.pattern.oops.SOLID.openClosePrinciple.before.taxes;

import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.Employee;

public class TaxCalculator {
    private final static int INCOME_TAX_PERCENTAGE = 20;
    private final static int PROFESSIONAL_TAX_PERCENTAGE = 3;


    public static double calculate(Employee employee) {
        return 
                (employee.getMonthlyIncome() * PROFESSIONAL_TAX_PERCENTAGE) / 100 +
                (employee.getMonthlyIncome() * INCOME_TAX_PERCENTAGE) / 100;

    }
}