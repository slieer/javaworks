package genericAlgorithms;

import java.lang.reflect.*;
import java.util.function.*;

/**
 * @version 1.1 2023-12-20
 * @author Cay Horstmann
 */
public class Pair<T> 
{
   private T first;
   private T second;

   public Pair() { first = null; second = null; }
   public Pair(T first, T second) { this.first = first;  this.second = second; }

   public T getFirst() { return first; }
   public T getSecond() { return second; }

   public void setFirst(T newValue) { first = newValue; }
   public void setSecond(T newValue) { second = newValue; }
   
   public String toString() { return "(" + first + ", " + second + ")"; }
   
   public static <T> Pair<T> makePair(Supplier<T> constr)
   {
      return new Pair<>(constr.get(), constr.get());
   }
   
   public static <T> Pair<T> makePair(Class<T> cl) throws ReflectiveOperationException
   {
      Constructor<T> constr = cl.getConstructor();
      return new Pair<>(constr.newInstance(), constr.newInstance()); 
   }        
}
