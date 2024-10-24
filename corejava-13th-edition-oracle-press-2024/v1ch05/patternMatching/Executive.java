package patternMatching;

public class Executive extends Manager
{
   private String title;

   public Executive(String name, String title, double salary, int year, int month, int day)
   {
      super(name, salary, year, month, day);
      this.title = title;;
   }

   public String getTitle()
   {
      return title;
   }
}

