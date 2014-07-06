package com.security.securityManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

class FileIO extends JFrame implements ActionListener {	
	JLabel text, clicked;
	JButton button, clickButton;
	JPanel panel;
	JTextField textField;

	FileIO() { // Begin Constructor
		setFont(new Font("Helvetica", Font.PLAIN, 14));
		text = new JLabel("Text to save to file:");
		clicked = new JLabel("Text retrieved from file:");

		button = new JButton("Click Me");
		button.addActionListener(this);

		clickButton = new JButton("Click Again");
		clickButton.addActionListener(this);

		textField = new JTextField(20);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		getContentPane().add(panel);
		panel.add("North", text);
		panel.add("Center", textField);
		panel.add("South", button);
	} // End Constructor

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == button) {
			JLabel label = new JLabel();

			try {
				// Write to file
				String text = textField.getText();
				byte b[] = text.getBytes();

				File outputFile = new File(PasswordSecurityManager.fileName2);
				//System.out.println("File Path:" + outputFile.getCanonicalFile());
				FileOutputStream out = new FileOutputStream(outputFile);
				out.write(b);
				out.close();
				// Read from file
				File inputFile = new File(PasswordSecurityManager.fileName2);
				FileInputStream in = new FileInputStream(inputFile);
				byte bt[] = new byte[(int) inputFile.length()];
				int i;
				i = in.read(bt);
				String s = new String(bt);
				label.setText(s);
				in.close();
			} catch (java.io.IOException e) {
				System.out.println(e.toString());
			}
			// Display text read from file
			panel.removeAll();
			panel.add("North", clicked);
			panel.add("Center", label);
			panel.add("South", clickButton);
			panel.validate();
			panel.repaint();
		}

		if (source == clickButton) {
			panel.removeAll();
			panel.add("North", text);
			textField.setText("");
			panel.add("Center", textField);
			panel.add("South", button);
			panel.validate();
			panel.repaint();
		}
	}

	public static void main(String[] args) {

		BufferedReader buffy = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			System.setSecurityManager(new PasswordSecurityManager("pwd", buffy));
		} catch (SecurityException se) {
			System.err.println("SecurityManager already set!");
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Couldn't use the cross-platform"
					+ "look and feel: " + e);
		}

		JFrame frame = new FileIO();
		frame.setTitle("Example");
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};

		frame.addWindowListener(l);
		frame.pack();
		frame.setVisible(true);
	}
}
