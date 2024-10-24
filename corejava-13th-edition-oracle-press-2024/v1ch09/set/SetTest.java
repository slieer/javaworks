package set;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * This program compares insertion into a hash set and a tree set.
 * Run the program as 
 * java set.SetTest ../gutenberg/alice30.txt
 * java set.SetTest ../gutenberg/crsto10.txt 100
 * @version 1.2 2023-12-07
 * @author Cay Horstmann
 */
public class SetTest
{
   public static void time(Set<String> wordSet, List<String> wordList, int repetitions) 
   {
      long totalTime = 0;
      for (int i = 1; i <= repetitions; i++) 
      {
         for (String word : wordList)
         {
            long start = System.nanoTime();
            wordSet.add(word);
            long end = System.nanoTime();
            totalTime += end - start;         
         }
      }
      Iterator<String> iter = wordSet.iterator(); 
      for (int i = 1; i <= 20 && iter.hasNext(); i++) 
         System.out.print(iter.next() + " ");
      System.out.println("...");
      System.out.printf("%s: %d words, %d distinct, %.3f seconds.%n",
         wordSet.getClass().getSimpleName(), wordList.size(), wordSet.size(), 
         totalTime * 1E-9);
   }
   
   public static void main(String[] args) throws IOException
   {
      List<String> words = new ArrayList<>();
      String filename = args.length > 0 ? args[0] : "../gutenberg/crsto10.txt";
      int repetitions = args.length > 1 ? Integer.parseInt(args[1]) : 1; 
      try (var in = new Scanner(Path.of(filename))) { 
         while (in.hasNext()) { 
            String word = in.next(); 
            words.add(word); 
         } 
      }      
      time(new HashSet<>(), words, repetitions);
      time(new TreeSet<>(), words, repetitions);
   }
}
