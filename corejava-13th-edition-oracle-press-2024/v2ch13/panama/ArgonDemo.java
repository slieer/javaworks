package panama;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.security.*;
import java.util.*;
import java.util.random.*;

/*

javac --enable-preview --source 21 panama/ArgonDemo.java 

java --enable-preview --enable-native-access=ALL-UNNAMED panama.ArgonDemo

*/

public class ArgonDemo
{
   public static void main(String[] args) throws Throwable
   {
      try (Arena arena = Arena.ofConfined())
      {
         SymbolLookup argon2 = SymbolLookup.libraryLookup("libargon2.so.1", arena);
         Linker linker = Linker.nativeLinker();
         MethodHandle argon2i_hash_raw = linker.downcallHandle(
            argon2.find("argon2i_hash_raw").get(),
            FunctionDescriptor.ofVoid(
               ValueLayout.JAVA_INT,
               ValueLayout.JAVA_INT,
               ValueLayout.JAVA_INT,
               ValueLayout.ADDRESS,
               ValueLayout.JAVA_INT,
               ValueLayout.ADDRESS,
               ValueLayout.JAVA_INT,
               ValueLayout.ADDRESS,
               ValueLayout.JAVA_INT));
         final int T_COST = 2;
         final int M_COST = 1 << 16;
         final int PARALLELISM = 1;
         String password = "secret";
         MemorySegment passwordSegment = arena.allocateUtf8String(password);
         RandomGenerator random = new SecureRandom();
         final int SALT_BYTES = 16;
         final int HASH_BYTES = 32;
         byte[] salt = new byte[SALT_BYTES];
         random.nextBytes(salt);
         MemorySegment saltSegment = arena.allocateArray(ValueLayout.JAVA_BYTE, salt);
         MemorySegment hashSegment = arena.allocate(HASH_BYTES);
         argon2i_hash_raw.invoke(T_COST, M_COST, PARALLELISM,
            passwordSegment, (int) passwordSegment.byteSize(),
            saltSegment, SALT_BYTES, hashSegment, HASH_BYTES);
         byte[] hash = hashSegment.toArray(ValueLayout.JAVA_BYTE);
         Base64.Encoder encoder = Base64.getEncoder();         
         System.out.println(T_COST + "|" + M_COST + "|" + PARALLELISM + "|"
            + encoder.encodeToString(salt) + "|" + encoder.encodeToString(hash));
      }
   }
}

   

