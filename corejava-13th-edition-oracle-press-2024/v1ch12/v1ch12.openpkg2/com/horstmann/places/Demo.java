package com.horstmann.places;

/*
 
javac -p jakarta.json.bind-api-3.0.0.jar \
    v1ch12.openpkg2/module-info.java \
    v1ch12.openpkg2/com/horstmann/places/Demo.java \
    v1ch12.openpkg2/com/horstmann/places/Country.java
 
java -p jakarta.json-api-2.1.2.jar:jakarta.json.bind-api-3.0.0.jar:\
parsson-1.1.4.jar:yasson-3.0.3.jar:v1ch12.openpkg2 \
    -m v1ch12.openpkg2/com.horstmann.places.Demo
 
*/

import jakarta.json.bind.*;
import jakarta.json.bind.config.*;

import java.lang.reflect.*;

public class Demo
{
   public static void main(String[] args)
   {
      Country belgium = new Country("Belgium", 30510);

      JsonbConfig config = new JsonbConfig()
         .withPropertyVisibilityStrategy(
            new PropertyVisibilityStrategy()
            {
               public boolean isVisible(Field field) { return true; }
               public boolean isVisible(Method method) { return false; }
            });
      Jsonb jsonb = JsonbBuilder.create(config);
      String json = jsonb.toJson(belgium);
      System.out.println(json);
   }
}
