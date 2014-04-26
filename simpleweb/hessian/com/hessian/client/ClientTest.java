package com.hessian.client;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hessian.services.BasicAPI;

public class ClientTest {
    public static void main(String[] args) {
        String url = "http://hessian.caucho.com/test/test";

        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            BasicAPI basic = null;
            basic = (BasicAPI) factory.create(BasicAPI.class, url);
            System.out.println("hello(): " + basic.hello());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
