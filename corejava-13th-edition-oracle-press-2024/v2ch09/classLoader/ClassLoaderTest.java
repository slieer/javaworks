package classLoader;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

/**
 * This program demonstrates a custom class loader that decrypts class files.
 * @version 1.3 2023-11-12
 * @author Cay Horstmann
 */
public class ClassLoaderTest
{
   public static void main(String[] args) throws ReflectiveOperationException
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Class name: ");
      String className = in.nextLine();
      System.out.print("Decryption key: ");
      int decryptionKey = in.nextInt();
      var loader = new CryptoClassLoader(decryptionKey);
      Class<?> c = loader.loadClass(className);
      Method m = c.getMethod("main", String[].class);
      m.invoke(null, (Object) new String[] {});
   }
}

/**
 * This class loader loads encrypted class files.
 */
class CryptoClassLoader extends ClassLoader
{
   private int key;

   /**
    * Constructs a crypto class loader.
    * @param k the decryption key
    */
   public CryptoClassLoader(int k)
   {
      key = k;
   }

   protected Class<?> findClass(String name) throws ClassNotFoundException
   {
      try
      {
         byte[] classBytes = loadClassBytes(name);
         Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
         if (cl == null) throw new ClassNotFoundException(name);
         return cl;
      }
      catch (IOException e)
      {
         throw new ClassNotFoundException(name);
      }
   }

   /**
    * Loads and decrypt the class file bytes.
    * @param name the class name
    * @return an array with the class file bytes
    */
   private byte[] loadClassBytes(String name) throws IOException
   {
      String cname = name.replace(".", "/") + ".caesar";
      byte[] bytes = Files.readAllBytes(Path.of(cname));
      for (int i = 0; i < bytes.length; i++)
         bytes[i] = (byte) (bytes[i] - key);
      return bytes;
   }
}
