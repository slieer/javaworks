package patternMatching;

record Point(double x, double y) {}

public class RecordPatternTest
{
   public static void main(String[] args)
   {
      int r = (int)(4 * Math.random());
      Point p = switch (r)
         {
            case 0 -> new Point(0, 0);
            case 1 -> new Point(1, 0);
            case 2 -> new Point(0, 1);
            default -> new Point(1, 1);
         };
      String description = switch (p)
         {
            case Point(var x, var y) when x == 0 && y == 0 -> "origin";
            case Point(var x, var __) when x == 0 -> "on x-axis";
            case Point(var __, var y) when y == 0 -> "on y-axis";
            default -> "not on either axis";
         };
      System.out.printf("%s %s%n", p, description);
   }
}
