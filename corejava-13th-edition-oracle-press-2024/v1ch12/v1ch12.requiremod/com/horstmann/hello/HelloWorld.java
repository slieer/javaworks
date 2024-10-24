package com.horstmann.hello;

/*

javac v1ch12.requiremod/module-info.java v1ch12.hellomod/com/horstmann/hello/HelloWorld.java 
java -p v1ch12.requiremod -m v1ch12.requiremod/com.horstmann.hello.HelloWorld

Remove the requires clause in the module descriptor and recompile to
see the failure

*/

import javax.swing.JOptionPane;

public class HelloWorld
{
   public static void main(String[] args)
   {
      JOptionPane.showMessageDialog(null, "Hello, Modular World!");
   }
}
