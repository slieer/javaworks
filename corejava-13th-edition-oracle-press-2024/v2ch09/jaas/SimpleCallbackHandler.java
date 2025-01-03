package jaas;

import javax.security.auth.callback.*;

/**
 * This simple callback handler presents the given user name and password.
 */
public class SimpleCallbackHandler implements CallbackHandler
{
   private String username;
   private char[] password;

   /**
    * Constructs the callback handler.
    * @param username the user name
    * @param password a character array containing the password
    */
   public SimpleCallbackHandler(String username, char[] password)
   {
      this.username = username;
      this.password = password;
   }

   public void handle(Callback[] callbacks)
   {
      for (Callback callback : callbacks)
      {
         switch (callback) 
         {
            case NameCallback c -> c.setName(username);
            case PasswordCallback c -> c.setPassword(password);
            default -> {}
         }
      }
   }
}
