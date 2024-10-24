package process;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * This program demonstrates running a process and reading its output, and listing all Java 
 * processes
 * @version 1.1 2023-10-11
 * @author Cay Horstmann
 */
public class ProcessDemo
{
   public static void main(String[] args) throws IOException, InterruptedException
   {
      Process p = new ProcessBuilder("/bin/ls", "-l")
         .directory(Path.of("/tmp").toFile())
         .start();
      try (var in = new Scanner(p.getInputStream()))
      {
         while (in.hasNextLine())
            System.out.println(in.nextLine());
      }
      System.out.println("pid: " + p.toHandle().pid());
      int result = p.waitFor();
      System.out.println("Exit value: " + result);

      ProcessHandle.allProcesses()
         .map(ProcessHandle::info)
         .filter(info -> info.command().filter(s -> s.contains("java")).isPresent())
         .forEach(info -> info.commandLine().ifPresent(System.out::println));
   }
}
