package com.security.jaas.login.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.security.jaas.login.SimpleCallbackHandler;

public class JAASFrame extends JFrame {
	private static final long serialVersionUID = 8288252519647998567L;

	public JAASFrame(){
		setTitle("JAASTest");
		username = new JTextField(20);
		password = new JPasswordField(20);
		propertyName = new JTextField(20);
		propertyValue = new JTextField(20);
		propertyValue.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(new JLabel("username:"));
		panel.add(username);
		
		panel.add(new JLabel("password"));
		panel.add(password);
		
		panel.add(propertyName);
		panel.add(propertyValue);
		
		add(panel,BorderLayout.CENTER);
		
		JButton getValueBuntion = new JButton("get value");
		getValueBuntion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getValue();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getValueBuntion);
		add(buttonPanel,BorderLayout.SOUTH);
		
		pack();
	}
	
	public void getValue(){
		try {
			LoginContext context = new LoginContext("Login1",new SimpleCallbackHandler(username.getText(), password.getPassword()));
			context.login();
			Subject subject = context.getSubject();
			
			SysPropAction sysPropAction =  new SysPropAction(propertyName.getText());
			propertyValue.setText("" + Subject.doAsPrivileged(subject, sysPropAction, null));
			
			context.logout();
		} catch (LoginException e) {
			JOptionPane.showMessageDialog(this, e);
			e.printStackTrace();
		}
	}
	
	private JTextField username;
	private JPasswordField password;
	private JTextField propertyName;
	private JTextField propertyValue;
}
