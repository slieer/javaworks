package com.security.securityManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;

public class PasswordSecurityManager extends SecurityManager {
	static String fileName1 = "wikis.sun/com/security/securityManager/test1.txt";
	static String fileName2 = "wikis.sun/com/security/securityManager/test2.txt";


	private String password;
	private BufferedReader buffy;

	public PasswordSecurityManager(String p, BufferedReader b) {
		super();
		this.password = p;
		this.buffy = b;
	}

	private boolean accessOK() {
		int c;
		String response;

		System.out.println("What's the secret password?");
		try {
			response = buffy.readLine();
			if (response.equals(password))
				return true;
			else
				return false;
		} catch (IOException e) {
			return false;
		}
	}

	public void checkRead(String filename) {
		// Mention file by name so don't get prompted for password
		// for everything the application loads to create itself

		if ((filename.equals(2))) {
			if (!accessOK()) {
				super.checkRead(filename);
				throw new SecurityException("No Way!");
			} else {
				FilePermission perm = new FilePermission(fileName2, "read");
				checkPermission(perm);
			}
		}
	}

	public void checkWrite(String filename) {
		// Mention file by name so don't get prompted for password
		// for everything the application loads to create itself
		if ((filename.equals(fileName1))) {
			if (!accessOK()) {
				super.checkWrite(filename);
				throw new SecurityException("No Way!");
			} else {
				FilePermission perm = new FilePermission(fileName1, "write");
				checkPermission(perm);
			}
		}
	}
}
