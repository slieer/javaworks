package com.security.tls_ssl.simpleDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	static int port = 50003;
    public static String encode = "utf-8";

	public static void main(String args[]) {
		try {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			Socket s = factory.createSocket("127.0.0.1", port);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), encode));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("证书启用成功!".getBytes(encode));
			System.out.println(in.readLine());
			out.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
