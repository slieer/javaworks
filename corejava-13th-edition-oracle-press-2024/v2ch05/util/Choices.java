package util;

import java.util.*;

public class Choices
{
   /**
    * Choose from an array of choices.
    * @param in the scanner from which to read the choice index
    * @param choices the strings to choose from
    * @return the chosen string
    */
   public static String choose(Scanner in, String... choices)
   {
      for (int i = 0; i < choices.length; i++)
      {
         System.out.printf("%2d: %s%n", i + 1, choices[i]);
      }
      while (true)
      {
         System.out.print("Your choice: ");
         if (in.hasNextInt())
         {
            int choice = in.nextInt();
            if (0 < choice && choice <= choices.length)
            {
               in.nextLine(); // Consume newline
               return choices[choice - 1];
            }
         }
         in.nextLine(); // Consume bad input
      }
   }
   
   /**
    * Choose from a list of choices.
    * @param in the scanner from which to read the choice index
    * @param choices the strings to choose from
    * @return the chosen string
    */
   public static String choose(Scanner in, List<String> choices)
   {
      return choose(in, choices.toArray(String[]::new));
   }
}
