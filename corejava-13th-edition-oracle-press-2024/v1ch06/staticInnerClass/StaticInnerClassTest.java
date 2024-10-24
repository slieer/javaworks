package staticInnerClass;

/**
 * This program demonstrates the use of static inner classes.
 * @version 1.1 2023-12-19
 * @author Cay Horstmann
 */
public class StaticInnerClassTest
{
   public static void main(String[] args)
   {      
      double[] numbers = { 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6 };
      ArrayAlg.Range r = ArrayAlg.longestRun(numbers);
      System.out.println("from = " + r.getFrom());
      System.out.println("to = " + r.getTo());
   }
}

class ArrayAlg
{
   /**
    * A range of index values.
    */
   public static class Range 
   {
      private int from;
      private int to;
      
      /**
       * Constructs a range of length 1.
       * @param from the initial index value of this range
       */
      public Range(int from) 
      {
         this.from = from;
         this.to = from + 1;
      }
      
      /**
       * Extends this range by one element.
       */
      public void extend() 
      {
         this.to++;
      }
      
      /**
       * Gets the starting index value of this range.
       * @return the starting index
       */
      public int getFrom()
      {
         return from;
      }
      
      /**
       * Gets the first index past the end of this range. 
       * @return the past-the-end index
       */
      public int getTo()
      {
         return to;
      }
      
      /**
       * Returns the number of elements in this range.
       * @return the number of elements
       */
      public int length() 
      {
         return to - from;
      }
   }

   /**
    * A "run" is a sequence of repeating adjacent elements. For example, in the array
    * 1 2 3 3 3 4 4, the runs are (trivially) 1 and 2, and 3 3 3 3 and 4 4.   
    * Returns the range of the longest run.
    * @param values an array of length at least 1 
    * @return the range of the longest run
    */
   public static Range longestRun(double[] values)
   {
      Range longest = new Range(0);
      Range current = new Range(0);
      for (int i = 1; i < values.length; i++) 
      {
         if (values[i] == values[i - 1]) current.extend();
         else 
         {
            if (longest.length() < current.length()) longest = current;
            current = new Range(i);
         }
      }
      if (longest.length() < current.length()) longest = current;
      return longest;      
   }      
}
