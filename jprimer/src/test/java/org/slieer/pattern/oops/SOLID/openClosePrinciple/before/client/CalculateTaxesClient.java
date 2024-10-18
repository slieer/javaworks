package org.slieer.pattern.oops.SOLID.openClosePrinciple.before.client;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.employees.Employee;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.persistence.EmployeeRepository;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.taxes.TaxCalculator;
import org.slieer.pattern.oops.SOLID.openClosePrinciple.before.taxes.TaxCalculator;


public class CalculateTaxesClient {
    public static void main(String[] args) {
       
        EmployeeRepository repository = new EmployeeRepository();

        // Grab employees
        List<Employee> employees = repository.findAll();

        // Calculate taxes
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
   

        double totalTaxes = 0;
        for (Employee employee: employees){

            // compute individual tax
            double tax = TaxCalculator.calculate(employee);
            String formattedTax = currencyFormatter.format(tax);
            // add to company total taxes
            totalTaxes += TaxCalculator.calculate(employee);
        }
    }
}
