package jaas;

import com.sun.security.auth.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

/**
 * This login module authenticates users by reading usernames, passwords, and roles from
 * a text file.
 */
public class SimpleLoginModule implements LoginModule
{
   private Subject subject;
   private CallbackHandler callbackHandler;
   private boolean debug;
   private String passwordFile;

   public void initialize(Subject subject, CallbackHandler callbackHandler,
         Map<String, ?> sharedState, Map<String, ?> options)
   {
      this.subject = subject;
      this.callbackHandler = callbackHandler;
      debug = options.get("debug").equals("true");
      passwordFile = "" + options.get("passwordFile");
   }

   public boolean login() throws LoginException
   {
      var nameCallback = new NameCallback("username: ");
      var passwordCallback = new PasswordCallback("password: ", false);
      try
      {
         if (debug) 
         {
            System.Logger logger = System.getLogger("com.horstmann.corejava"); 
            logger.log(System.Logger.Level.INFO, "Invoking handler");
         }
         if (callbackHandler == null) throw new LoginException("no handler");

         callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
         return checkLogin(nameCallback.getName(), passwordCallback.getPassword());
      }
      catch (UnsupportedCallbackException | IOException e)
      {
         var e2 = new LoginException();
         e2.initCause(e);
         throw e2;
      }
   }

   /**
    * Checks whether the authentication information is valid. If it is, the subject acquires
    * principals for the user name and role.
    * @param username the user name
    * @param password a character array containing the password
    * @return true if the authentication information is valid
    */
   private boolean checkLogin(String username, char[] password) 
         throws IOException
   {
      try (var in = new Scanner(Path.of(passwordFile)))
      {
         while (in.hasNextLine())
         {
            String[] inputs = in.nextLine().split("\\|");
            if (inputs[0].equals(username) 
                  && Arrays.equals(inputs[1].toCharArray(), password))
            {
               String role = inputs[2];
               Set<Principal> principals = subject.getPrincipals();
               principals.add(new UserPrincipal(username));
               principals.add(new RolePrincipal(role));
               return true;
            }
         }
         return false;
      }
   }

   public boolean logout()
   {
      return true;
   }

   public boolean abort()
   {
      return true;
   }

   public boolean commit()
   {
      return true;
   }
}
