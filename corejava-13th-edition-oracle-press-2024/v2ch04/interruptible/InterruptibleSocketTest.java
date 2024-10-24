package interruptible;

import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.channels.*;

/**
 * This program shows how to interrupt a socket channel.
 * @author Cay Horstmann
 * @version 1.1 2023-08-16
 */
public class InterruptibleSocketTest
{
   public static void main(String[] args)
   {      
      Thread.ofPlatform().start(new TestServer());
      Scanner console = new Scanner(System.in);
      System.out.print("Socket or channel? (S/C) ");
      boolean socket = console.nextLine().equalsIgnoreCase("S");
      System.out.print("Platform or virtual? (P/V) ");
      boolean platform = console.nextLine().equalsIgnoreCase("P");
      Runnable client = socket ? InterruptibleSocketTest::useSocket
            : InterruptibleSocketTest::useChannel;
      Thread clientThread = platform ? Thread.ofPlatform().start(client) 
            : Thread.ofVirtual().start(client);
         
      System.out.print("Hit Enter to interrupt client");
      console.nextLine();
      clientThread.interrupt();
   }

   /**
    * Connects to the test server, using a channel.
    */
   public static void useChannel()
   {
      System.out.println("Channel:\n");
      try (SocketChannel channel 
            = SocketChannel.open(new InetSocketAddress("localhost", 8189)))
      {
         Scanner in = new Scanner(channel);
         while (!Thread.currentThread().isInterrupted())
         {
            System.out.print("Reading ");
            if (in.hasNextLine())
            {
               String line = in.nextLine();
               System.out.println(line);
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      finally
      {
         System.out.println("Channel closed\n");
      }
   }

   /**
    * Connects to the test server, using a socket.
    */
   public static void useSocket()
   {
      System.out.println("Socket:\n");
      try (var sock = new Socket("localhost", 8189))
      {
         Scanner in = new Scanner(sock.getInputStream());
         while (!Thread.currentThread().isInterrupted())
         {
            System.out.print("Reading ");
            if (in.hasNextLine())
            {
               String line = in.nextLine();
               System.out.println(line);
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      finally
      {
         System.out.println("Socket closed\n");
      }
   }

   /**
    * A server that listens to port 8189 and sends numbers to the client,
    * simulating a hanging server after 10 numbers.
    */
   static class TestServer implements Runnable
   {
      public void run()
      {
         try (var s = new ServerSocket(8189);
            Socket incoming = s.accept())
         {
            serve(incoming);
         }
         catch (Exception e)
         {
            e.printStackTrace();;
         }
         finally
         {
            System.out.println("Closing connection\n");
         }
      }

      private void serve(Socket incoming) throws IOException, InterruptedException
      {
         int counter = 0;
         try (var out = new PrintWriter(incoming.getOutputStream(),
               true /* autoFlush */))
         {
            while (counter < 100)
            {
               counter++;
               if (counter <= 10) out.println(counter);
               Thread.sleep(100);
            }
         }
      }
   }
}

