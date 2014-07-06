package com.hessian.services;

import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements BasicAPI {
    private static final long serialVersionUID = -8122102759703552769L;
    
    private String _greeting = "Hello, world";

    public void setGreeting(String greeting) {
        _greeting = greeting;
    }

    public String hello() {
        return _greeting;
    }
}
