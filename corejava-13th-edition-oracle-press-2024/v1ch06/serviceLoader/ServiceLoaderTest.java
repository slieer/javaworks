package serviceLoader;

import java.io.*;
import java.util.*;

/**
 * @version 1.02 2023-08-16
 * @author Cay Horstmann
 */

public class ServiceLoaderTest
{
   public static ServiceLoader<Cipher> cipherLoader 
      = ServiceLoader.load(Cipher.class);

   public static void main(String[] args) throws UnsupportedEncodingException
   {
      Cipher cipher = getCipher(1);
      String message = "Meet me at the toga party.";
      byte[] bytes = cipher.encrypt(message.getBytes(), new byte[] { 3 });
      var encrypted = new String(bytes);
      System.out.println(encrypted);
   }

   public static Cipher getCipher(int minStrength)
   {
      for (Cipher cipher : cipherLoader)
         // Implicitly calls iterator
         if (cipher.strength() >= minStrength) return cipher;
      return null;
   }
}
