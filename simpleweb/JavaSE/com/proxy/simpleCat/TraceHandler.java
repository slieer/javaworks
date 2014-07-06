package com.proxy.simpleCat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TraceHandler implements InvocationHandler{
    
    private Object target;//以真实角色作为代理角色的属性
    
    //构造器
    public TraceHandler(Object target) {   
       this.target = target;   
   }   

   /*
    * 通过反射机制动态执行真实角色的每一个方法
    */
   public Object invoke(Object proxy, Method method, Object[] args)
           throws Throwable {
       try {
           System.out.println("被拦截的方法:" + method.getName()); 
           System.out.println("预处理.");
           
           return method.invoke(target, args);//调用真是对象的method方法
           
       } catch (Exception e) {
           return null;
       } finally {
            System.out.println("善后处理.");
       }
   }
}
