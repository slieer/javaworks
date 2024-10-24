package com.horstmann.places;

/*

javac com.horstmann.util/module-info.java \
   com.horstmann.util/com/horstmann/util/ObjectAnalyzer.java
javac -p com.horstmann.util v1ch12.openpkg/module-info.java \
   v1ch12.openpkg/com/horstmann/places/*.java
java -p v1ch12.openpkg:com.horstmann.util -m v1ch12.openpkg/com.horstmann.places.Demo

Remove the opens clause in the module descriptor and recompile/run to
see the failure

*/

import com.horstmann.util.*;

public class Demo
{
   public static void main(String[] args) throws ReflectiveOperationException
   {
      Country belgium = new Country("Belgium", 30510);
      ObjectAnalyzer analyzer = new ObjectAnalyzer();
      System.out.println(analyzer.toString(belgium));
   }
}
