package streams;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;

public class FilterMapDemo
{
   public static <T> void show(String title, Stream<T> stream)
   {
      final int SIZE = 10;
      List<T> firstElements = stream.limit(SIZE + 1).toList();
      System.out.print(title + ": ");
      if (firstElements.size() <= SIZE)
         System.out.println(firstElements);
      else
      {
         String out = firstElements.subList(0, SIZE).toString();
         System.out.println(out.substring(0, out.length() - 1) + ", ...]");
      }
   }

   @SuppressWarnings("resource")
   public static Stream<String> graphemeClusters(String s)
   {
      return new Scanner(s).useDelimiter("\\b{g}").tokens();
   }

   public static void main(String[] args) throws IOException
   {
      String contents = Files.readString(Path.of("../gutenberg/alice30.txt"));
      List<String> words = List.of(contents.split("\\PL+"));
      Stream<String> longWords = words.stream().filter(w -> w.length() > 12);
      show("longWords", longWords);

      Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
      show("lowercaseWords", lowercaseWords);

      String[] song = { "row", "row", "row", "your", "boat", "gently", "down",
                        "the", "stream" };
      Stream<Character> firstCodeUnits = Stream.of(song).filter(w -> w.length() > 0).map(s -> s.charAt(0));
      show("firstCodeUnits", firstCodeUnits);

      Stream<String> letters = Stream.of(song).flatMap(w -> graphemeClusters(w));
      show("letters", letters);

      BreakIterator iter = BreakIterator.getCharacterInstance();
      Stream<String> result = words.stream().mapMulti((s, collector) ->
         {
            iter.setText(s);
            int start = iter.first();
            int end = iter.next();
            while (end != BreakIterator.DONE)
            {
               String gc = s.substring(start, end);
               start = end;
               end = iter.next();
               collector.accept(gc);
            }
         });
      show("result", result);
   }
}
