package com.proxy.reflect;

import java.lang.reflect.Method;

public class ReflectBean {
    
    public String ff(String xx){
        return xx.concat(" slieer !");
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        ReflectBean b = new ReflectBean();
        
        Method method;
        try {
            method = ReflectBean.class.getMethod("ff", String.class);
            System.out.println("method:" + method);
            
            String mntPath = (String)method.invoke(b, "abc");
            System.out.println(mntPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
