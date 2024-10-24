package memoryMap;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * This program computes the CRC checksum of a file in four ways. <br>
 * Usage: java memoryMap.MemoryMapTest filename
 * 
 * @version 1.03 2018-05-01
 * @author Cay Horstmann
 */
public class MemoryMapTest
{
   public static long checksumInputStream(Path filename) throws IOException
   {
      try (InputStream in = Files.newInputStream(filename))
      {
         var crc = new CRC32();
         boolean done = false;
         while (!done)
         {
            int c = in.read();
            if (c == -1)
               done = true;
            else
               crc.update(c);
         }
         return crc.getValue();
      }
   }

   public static long checksumBufferedInputStream(Path filename) throws IOException
   {
      try (var in = new BufferedInputStream(Files.newInputStream(filename)))
      {
         var crc = new CRC32();

         boolean done = false;
         while (!done)
         {
            int c = in.read();
            if (c == -1)
               done = true;
            else
               crc.update(c);
         }
         return crc.getValue();
      }
   }

   public static long checksumRandomAccessFile(Path filename) throws IOException
   {
      try (var file = new RandomAccessFile(filename.toFile(), "r"))
      {
         long length = file.length();
         var crc = new CRC32();

         for (long p = 0; p < length; p++)
         {
            int c = file.readByte();
            crc.update(c);
         }
         return crc.getValue();
      }
   }

   public static long checksumMappedFile(Path filename) throws IOException
   {
      try (FileChannel channel = FileChannel.open(filename))
      {
         var crc = new CRC32();
         int length = (int) channel.size();
         MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

         for (int p = 0; p < length; p++)
         {
            int c = buffer.get(p);
            crc.update(c);
         }
         return crc.getValue();
      }
   }

   public static void main(String[] args) throws IOException
   {
      System.out.println("Input Stream:");
      long start = System.nanoTime();
      Path filename = Path.of(args[0]);
      long crcValue = checksumInputStream(filename);
      long end = System.nanoTime();
      System.out.println(Long.toHexString(crcValue));
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);

      System.out.println("Buffered Input Stream:");
      start = System.nanoTime();
      crcValue = checksumBufferedInputStream(filename);
      end = System.nanoTime();
      System.out.println(Long.toHexString(crcValue));
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);

      System.out.println("Random Access File:");
      start = System.nanoTime();
      crcValue = checksumRandomAccessFile(filename);
      end = System.nanoTime();
      System.out.println(Long.toHexString(crcValue));
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);

      System.out.println("Mapped File:");
      start = System.nanoTime();
      crcValue = checksumMappedFile(filename);
      end = System.nanoTime();
      System.out.println(Long.toHexString(crcValue));
      System.out.printf("%.3f seconds%n", (end - start) * 1E-9);
   }
}
