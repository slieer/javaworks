package com.horstmann.places;

/*

javac -p v1ch12.automod:commons-csv-1.10.0.jar \
   v1ch12.automod/com/horstmann/places/CSVDemo.java \
   v1ch12.automod/module-info.java
java -p v1ch12.automod:commons-csv-1.10.0.jar \
   -m v1ch12.automod/com.horstmann.places.CSVDemo 
 
*/

import java.io.*;
import org.apache.commons.csv.*;

public class CSVDemo 
{
   public static void main(String[] args) throws IOException 
   {
      Reader in = new FileReader("countries.csv");
      Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withHeader().parse(in);
      for (CSVRecord record : records) 
      {
         String name = record.get("Name");
         double area = Double.parseDouble(record.get("Area"));
         System.out.println(name + " has area " + area);
      }
   }
}
