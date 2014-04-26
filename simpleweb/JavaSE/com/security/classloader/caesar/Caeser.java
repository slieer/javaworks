package com.security.classloader.caesar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Caeser {
	private static byte c = 3;
	public static File f = new File("target/classes/com/security/classloader/TestAction.class");
	public static File targetFile = new File("target/classes/com/security/classloader/TestAction.caeser");

	public static void main(String[] args) {
		if(f.exists() && f.isFile()){
			if(targetFile.exists()){
				targetFile.delete();
			}
			encryptClass(f);
			System.out.println("succussful.");
		}
	}

	/**
	 * 加密类文件
	 * 
	 * @throws IOException
	 */
	public static void encryptClass(File f) {
		if (f.isDirectory()) {
			throw new IllegalStateException("input file path");
		}

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(f);
			String name = f.getAbsolutePath();
			//String newName = name.replaceAll(".class", ".caeser");
			out = new FileOutputStream(targetFile);

			int ch = -1;
			while ((ch = in.read()) != -1) {
				ch += c;
				out.write((byte) ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
