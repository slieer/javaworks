package script;

import java.io.*;
import java.nio.file.*;
import javax.script.*;

/**
 * @version 1.1 2023-08-17
 * @author Cay Horstmann
 * 

Download Rhino and the Rhino engine JAR from 
https://mvnrepository.com/artifact/org.mozilla/rhino
https://mvnrepository.com/artifact/org.mozilla/rhino-engine
Place in a directory rhino

javac script/ScriptTest.java
java --classpath .:rhino/\* script.ScriptTest rhino script/sheet

 */
public class ScriptTest
{
   public static void main(String[] args) throws ScriptException, IOException
   {
      var manager = new ScriptEngineManager();
      String language;
      if (args.length == 0) 
      {
         System.out.println("Available factories: ");
         for (ScriptEngineFactory factory : manager.getEngineFactories())
            System.out.println(factory.getEngineName());
         return;
      }
      else language = args[0];

      ScriptEngine engine = manager.getEngineByName(language);               
      if (engine == null)
      {
         System.err.println("No engine for " + language);
         System.exit(1);
      }

      String sheetName = args[1];
      boolean verbatim = false;
      var verbatimCode = new StringBuilder(); 
      for (String line : Files.readAllLines(Path.of(sheetName)))
      {
         if (line.equals("```")) {
            verbatim = !verbatim;
            if (!verbatim)
            {
               engine.eval(verbatimCode.toString());
               verbatimCode.delete(0, verbatimCode.length());
            }
         }
         else if (verbatim) verbatimCode.append(line).append("\n");
         else
         {
            String[] fragments = line.split("`");
            for (int i = 0; i < fragments.length; i++)
            {
               System.out.print(i % 2 == 0 ? fragments[i] : engine.eval(fragments[i]));
            }
            System.out.println();
         }
      }      
   }
}
