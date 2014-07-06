package com.security.classloader.caesar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CryptoClassLoader extends ClassLoader {
	private int key;

	public CryptoClassLoader(int key) {
		super();
		this.key = key;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		byte[] classBytes = null;
		classBytes = loadClassBytes(name);
		if (classBytes == null) {
			throw new ClassNotFoundException();
		}

		Class c1 = defineClass(name, classBytes, 0, classBytes.length);
		if (c1 == null) {
			throw new ClassNotFoundException();
		}
		return c1;
	}

	private byte[] loadClassBytes(String name) {
		String cName = name.replace(".", "/");
		FileInputStream in = null;

		try {
			File f = new File(cName);
			in = new FileInputStream(f);

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				byte b = (byte) (ch - key);

				buffer.write(b);
			}
			return buffer.toByteArray();
		} catch (Exception e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
