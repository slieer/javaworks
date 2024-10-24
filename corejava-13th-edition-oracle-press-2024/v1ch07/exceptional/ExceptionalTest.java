package exceptional;

import java.util.*;

/**
 * @version 1.13 2023-12-07
 * @author Cay Horstmann
 */
public class ExceptionalTest
{
   public static void main(String[] args)
   {
      int i = 0;
      int ntry = 10000000;
      var stack = new Stack<String>();

      // test a stack for emptiness ntry times
      System.out.println("Testing for empty stack");
      long start = System.nanoTime();
      for (i = 0; i <= ntry; i++)
         if (!stack.empty()) stack.pop();
      long end = System.nanoTime();
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);

      // pop an empty stack ntry times and catch the resulting exception
      System.out.println("Catching EmptyStackException");
      start = System.nanoTime();
      for (i = 0; i <= ntry; i++)
      {
         try
         {
            stack.pop();
         }
         catch (EmptyStackException e)
         {
         }
      }
      end = System.nanoTime();
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);
   }
}
