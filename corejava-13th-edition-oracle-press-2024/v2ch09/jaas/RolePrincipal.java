package jaas;

import java.security.*;

/**
 * A principal with a named role.
 */
public record RolePrincipal(String name) implements Principal
{
   /**
    * @return the role name.
    */
   public String getName()
   {
      return name;
   }
}
