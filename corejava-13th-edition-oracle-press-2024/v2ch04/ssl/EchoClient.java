package ssl;

import java.io.*;
import java.util.*;

import java.net.*;
import javax.net.*;
import javax.net.ssl.*;

/**
 * This program makes a socket connection to the atomic clock in Boulder, Colorado, and prints 
 * the time that the server sends.
 * @version 1.23 2023-08-16
 * @author Cay Horstmann
 */
public class EchoClient
{
   public static void main(String[] args) throws IOException
   {
      int port = args.length >= 1 ? Integer.parseInt(args[0]) : 8189;
      SocketFactory factory = SSLSocketFactory.getDefault();
      String message = """
Hello
World
BYE
""";
      try (Socket s = factory.createSocket(InetAddress.getLocalHost(), port);
         OutputStream os = s.getOutputStream();
         var in = new Scanner(s.getInputStream()))
      {
         os.write(message.getBytes());
         os.flush();
         boolean done = false;
         while (!done)
         {
            String line = in.nextLine();
            System.out.println(line);
            if (line.equals("Echo: BYE")) done = true;
         }
      }
   }
}
