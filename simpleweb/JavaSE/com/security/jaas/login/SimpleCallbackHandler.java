package com.security.jaas.login;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class SimpleCallbackHandler implements CallbackHandler {
	
	public SimpleCallbackHandler(String username, char[] passwrod) {
		super();
		this.username = username;
		this.password = passwrod;
	}
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for(Callback callback : callbacks){
			if(callback instanceof NameCallback){
				((NameCallback)callback).setName(username);
			}else if(callback instanceof PasswordCallback){
				((PasswordCallback)callback).setPassword(password);
			}
		}
	}
	
	private String username;
	private char[] password;
}
