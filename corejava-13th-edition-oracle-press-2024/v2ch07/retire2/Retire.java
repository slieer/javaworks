package retire2;

import java.text.*;
import java.util.*;

import util.Choices;

/**
 * This program shows a retirement calculator. The prompts are displayed in English, German, 
 * or Chinese.
 * @version 1.3 2023-11-07
 * @author Cay Horstmann
 */
public class Retire
{
   public static void main(String[] args)
   {
      // Get locale
      ResourceBundle bundle 
         = ResourceBundle.getBundle("retire.RetireStrings", Locale.getDefault());      
      System.out.println(bundle.getString("language"));
      Scanner in = new Scanner(System.in);
      Locale[] locales = { Locale.US, Locale.CHINA, Locale.GERMANY };
      Locale currentLocale = Choices.choose(in, locales, Locale::getDisplayName);

      bundle = ResourceBundle.getBundle("retire.RetireStrings", currentLocale);      

      // Prompt for parameters
      System.out.print(bundle.getString("savings") + ": ");
      double savings = in.nextDouble();
      
      System.out.print(bundle.getString("contributions") + ": ");
      double contributions = in.nextDouble();
      
      System.out.print(bundle.getString("income") + ": ");
      double income = in.nextDouble();

      System.out.print(bundle.getString("currentAge") + ": ");
      int currentAge = in.nextInt();

      System.out.print(bundle.getString("retireAge") + ": ");
      int retireAge = in.nextInt();

      System.out.print(bundle.getString("deathAge") + ": ");
      int deathAge = in.nextInt();

      System.out.print(bundle.getString("inflationPercent") + ": ");
      double inflationPercent = in.nextDouble();
      
      System.out.print(bundle.getString("investPercent") + ": ");
      double investPercent = in.nextDouble();
            
      // Show values
      var retireMsg = new MessageFormat("");
      retireMsg.setLocale(currentLocale);
      retireMsg.applyPattern(bundle.getString("retire"));

      double balance = savings;
      for (int year = currentAge; year <= deathAge; year++)
      {
         Object[] msgArgs = { year, balance };
         System.out.println(retireMsg.format(msgArgs));
         if (year < retireAge) balance += contributions;
         else balance -= income;
         balance = balance * (1 + (investPercent - inflationPercent));         
      }     
   }
}