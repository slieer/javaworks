package panama;

import java.lang.invoke.*;
import java.lang.foreign.*;

/*

javac --enable-preview --source 21 panama/PanamaDemo.java 

java --enable-preview --enable-native-access=ALL-UNNAMED panama.PanamaDemo

*/

public class PanamaDemo
{
   public static void main(String[] args) throws Throwable
   {
      Linker linker = Linker.nativeLinker();
      MethodHandle printf = linker.downcallHandle(
         linker.defaultLookup().find("printf").get(),
         FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));

      try (Arena arena = Arena.ofConfined())
      {
         MemorySegment str = arena.allocateUtf8String("Hello, World!\n");
         int result = (int) printf.invoke(str);
         System.out.printf("Printed %d characters.%n", result);
      }
   }
}
