package threaded;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * This program implements a multithreaded server that listens to port 8189 and echoes back 
 * all client input.
 * @author Cay Horstmann
 * @version 1.24 2023-08-16
 */
public class EchoServer
{  
   public static void main(String[] args) throws IOException
   {  
      try (var s = new ServerSocket(8189))
      {  
         int i = 1;
         ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();

         while (true)
         {  
            Socket incoming = s.accept();
            System.out.println("Spawning " + i);
            service.submit(() -> serve(incoming));
            i++;
         }
      }
   }
   
   public static void serve(Socket incoming)
   {
      try (var in = new Scanner(incoming.getInputStream());         
         var out = new PrintWriter(incoming.getOutputStream(), 
            true /* autoFlush */))
      {                        
         out.println( "Hello! Enter BYE to exit." );
            
         // echo client input
         boolean done = false;
         while (!done && in.hasNextLine())
         {  
            String line = in.nextLine();            
            out.println("Echo: " + line);            
            if (line.strip().equals("BYE"))
               done = true;
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }              
   }
}
