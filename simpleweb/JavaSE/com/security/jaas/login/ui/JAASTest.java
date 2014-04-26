package com.security.jaas.login.ui;

import javax.swing.JFrame;

public class JAASTest {
	public static void main(String...args){
		System.setSecurityManager(new SecurityManager());
		JFrame frame = new JAASFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
