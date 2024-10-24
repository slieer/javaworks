package runtimeAnnotations;

public class RuntimeAnnotationDemo
{
   public static void main(String[] args)
   {
      var rect = new Rectangle(new Point(5, 10), 20, 30);
      System.out.println(ToStrings.toString(rect));
   }
}
