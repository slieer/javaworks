package com.net.netserializable.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.net.netserializable.Student;

class MyClient {
	public static void main(String[] args) {
		f();
	}
	
	
	class UserThread  extends Thread{
		@Override
		public void run() {
			f();
		}		
	}
	
	static void  f(){
		try {
			System.out.println("runniing...");
			Socket s = new Socket("localhost", 9999);
			InputStream in = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			Student stu = (Student) ois.readObject();
			System.out.println("客户端程序收到服务器端程序传输过来的学生对象>> " + stu);
			ois.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
