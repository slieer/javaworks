package ssl;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javax.net.*;
import javax.net.ssl.*;

/**
 * This program implements a simple server that listens to port 8189 
 * or a port given as argument, and echoes back all client input.
 * @version 1.0 2023-08-16
 * @author Cay Horstmann
 */
public class EchoServer
{
   public static void main(String[] args) throws IOException
   {
      int port = args.length >= 1 ? Integer.parseInt(args[0]) : 8189;
      // establish server socket
      ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
      try (ServerSocket s = factory.createServerSocket(port))
      {
         ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
         
         while (true)
         {
            // wait for client connection
            Socket incoming = s.accept();
            service.submit(() -> serve(incoming));
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
