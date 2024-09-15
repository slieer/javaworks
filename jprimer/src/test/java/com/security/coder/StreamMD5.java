package com.security.coder;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class StreamMD5 {
	public static void main(String[] args) throws Exception {
		String fileName = "test.txt";
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fin = new FileInputStream(fileName);
		DigestInputStream din = new DigestInputStream(fin, md);// 构造输入流
		
		// DigestOutputStream dout = new DigestOutputStream(fout,md);
		// 使用输入（出）流可以自己控制何时开始和关闭计算摘要
		// 也可以不控制，将全过程计算
		// 初始时是从开始即开始计算，如我们可以开始时关闭，然后从某一部分开始，如下：
		// din.on(false);
		int b;
		while ((b = din.read()) != -1) {
			// 做一些对文件的处理
			// if(b=='$') din.on(true); //当遇到文件中的符号$时才开始计算
		}
		
		
		byte[] re = md.digest();// 获得消息摘要
		// 下面把消息摘要转换为字符串
		String result = "";
		for (int i = 0; i < re.length; i++) {
			result += Integer.toHexString((0x000000ff & re[i]) | 0xffffff00)
					.substring(6);
		}
		System.out.println(result);
	}
}
