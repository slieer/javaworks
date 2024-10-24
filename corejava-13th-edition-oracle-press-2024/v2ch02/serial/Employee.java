package serial;

import java.io.*;
import java.time.*;
import java.util.*;

public class Employee implements Serializable
{
   private String name;
   private double salary;
   private Date hireDay;

   public Employee()
   {
   }

   public Employee(String n, double s, int year, int month, int day)
   {
      name = n;
      salary = s;
      hireDay = Date.from(
         LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant()); 
   }

   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public Date getHireDay()
   {
      return hireDay;
   }

   /**
    * Raises the salary of this employee.
    * 
    * @param byPercent the percentage of the raise
    */
   public void raiseSalary(double byPercent)
   {
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {
      return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
   }
}
