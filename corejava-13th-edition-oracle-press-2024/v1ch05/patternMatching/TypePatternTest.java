package patternMatching;

public class TypePatternTest
{
   public static void main(String[] args)
   {
      int r = (int)(4 * Math.random());
      Employee e = switch (r)
         {
            case 0 -> new Employee("Harry Hacker", 50000, 1989, 10, 1);
            case 1 -> new Manager("Carl Cracker", 80000, 1987, 12, 15);
            case 2 -> new Executive("Sue Striver", 
                  "Senior Associate Vice President", 200000, 1995, 1, 20);
            default -> null;
         };
      String description = switch (e)
         {
            case Executive exec when exec.getTitle().length() >= 20 ->
               "An executive with an impressive title";
            case Executive exec ->
               "An executive with a title of " + exec.getTitle();
            case Manager m ->
               {
                  m.setBonus(10000); 
                  yield "A manager who just got a bonus";
               }
            case null -> "No employee";
            default -> "A lowly employee with a salary of " + e.getSalary();
         };
      System.out.println(description);
   }
}
