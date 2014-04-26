package com.security.jaas.login;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SimpleLoginModule implements LoginModule {
	public static final String USER_NAME = "username";
	public static final String ROLE = "role";
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, 
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.callbackHandler = callbackHandler;
		this.options = options;
		this.sharedState = sharedState;
		this.subject = subject;
	}
	
	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		return false;
	}

	@Override
	public boolean login() throws LoginException {
		if(callbackHandler == null){
			throw new LoginException("no handler");
		}
		
		NameCallback nameCall = new NameCallback("username:");
		PasswordCallback passCall = new PasswordCallback("password:", false);
		
		try {
			callbackHandler.handle(new Callback[]{nameCall,passCall});
			
		} catch (UnsupportedCallbackException e) {
			LoginException e2 = new LoginException("Unsupported callback");
			e2.initCause(e);
			throw e2;
		} catch (IOException e) {			
			LoginException e2 = new LoginException("I/O exception in callback");
			e2.initCause(e);
			throw e2;
		} 
		return checkLogin(nameCall.getName(),passCall.getPassword());
	}

	@Override
	public boolean logout() throws LoginException {
		return false;
	}
	
	private boolean checkLogin(String userName,char[] password)throws LoginException{
		try {
			Scanner in = new Scanner(new FileReader("" + options.get("pwfine")));
			while(in.hasNext()){
				String inputs[] = in.nextLine().split("\\|");
				if(inputs[0].equals(userName) && Arrays.equals(inputs[1].toCharArray(), password)){
					String role = inputs[2];
					Set<Principal> principals = subject.getPrincipals();
					principals.add(new SimplePrincipal(USER_NAME, userName));
					principals.add(new SimplePrincipal(ROLE,role));					
					return true;
				}
			}
			in.close();
			return false;
		} catch (FileNotFoundException e) {
			LoginException e1 = new LoginException("password file not exist.");
			e1.initCause(e);
			throw e1;
		}
	}
	
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;
}
