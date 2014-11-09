package com.web.php;

import com.caucho.quercus.module.AbstractQuercusModule;

public class HelloModule extends AbstractQuercusModule {
   /*
   ** Notice the careful use of the naming
   ** convention hello_test.  This is done
   ** in order to prevent name collisions
   ** among different libraries.
   */
   public String hello_test(String name)
   {
     return "Hello, " + name;
   }
}
