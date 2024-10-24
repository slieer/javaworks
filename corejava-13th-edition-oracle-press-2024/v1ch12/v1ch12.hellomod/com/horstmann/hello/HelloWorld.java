package com.horstmann.hello;

/*

javac v1ch12.hellomod/module-info.java v1ch12.hellomod/com/horstmann/hello/HelloWorld.java 
java --module-path v1ch12.hellomod --module v1ch12.hellomod/com.horstmann.hello.HelloWorld

*/

public class HelloWorld
{
   public static void main(String[] args)
   {
      System.out.println("Hello, Modular World!");
   }
}
