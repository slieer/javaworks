package com.security.classloader.caesar;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClassLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new ClassLoaderFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class ClassLoaderFrame extends JFrame {
	private static final long serialVersionUID = -129943192017969369L;

	public ClassLoaderFrame() {
		setTitle("class loader Test");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGH);
		setLayout(new GridLayout());

		add(new JLabel("Class"));
		nameField.setSize(300, 50);
		add(nameField);
		add(new JLabel("key"));
		add(keyField);

		JButton button = new JButton("submit");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runClass(nameField.getText(),keyField.getText());
			}
		});
		add(button);
	}

	public void runClass(String name, String key) {
		ClassLoader loader = new CryptoClassLoader(Integer.valueOf(key));
		try {
			@SuppressWarnings("rawtypes")
			Class c = loader.loadClass(name);
			String[] args = new String[] {};
			
			@SuppressWarnings("unchecked")
			Method m = c.getMethod("main", args.getClass());
			m.invoke(null, (Object) args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JTextField keyField = new JTextField("3", 4);
	private JTextField nameField = new JTextField(Caeser.targetFile.getAbsolutePath(),2);
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGH = 100;
}