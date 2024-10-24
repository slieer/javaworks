package jaas;

import com.sun.security.auth.callback.*;
import java.io.*;
import java.security.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;

/**
 * This program shows how to authenticate a user via a custom login 
 * @version 1.1 2023-11-12
 * @author Cay Horstmann
 */
public class JAASTest
{
   public static void main(final String[] args)
   {
      CallbackHandler handler;
      if (args.length == 0) handler = new TextCallbackHandler();
      else
      {
         // In most apps, you have your own way of reading the username and password.
         // Here, the user provides the user name as a command line argument and the password 
         // on the console.
         String username = args[0];
         Console console = System.console();
         char[] password = console.readPassword("Password: ");
         handler = new SimpleCallbackHandler(username, password);
      }
      
      try
      {
         var context = new LoginContext("Login1", handler);
         context.login();
         Subject subject = context.getSubject();
         for (Principal p : subject.getPrincipals())
         {
            System.out.println(p.getClass().getName() + " " + p.getName());
         }
         context.logout();
      }
      catch (LoginException e)
      {
         e.printStackTrace();
         Throwable cause = e.getCause();
         if (cause != null) cause.printStackTrace();         
      }
   }
}