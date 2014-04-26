package com.net.netserializable.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.net.netserializable.Student;

/**
 * telnet localhost 9999 to test
 * @author me
 *
 */
public class BasicServer {
	public static void main(String[] args) {
		int i = 0;
		try {
			ServerSocket s = new ServerSocket(9999);
			while(true){
				Socket incoming = s.accept();
				Runnable r = new IoHandler(incoming, i);
				Thread thread = new Thread(r);
				thread.start();
				i++;
				//ExecutorService serv = Executors.newCachedThreadPool();
				//serv.submit(new IoHandler(incoming, i));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}

class IoHandler implements Runnable{
	private Socket incoming;
	private int counter;
	
	public IoHandler(Socket s, int count){
		this.incoming = s;
		this.counter = count;
	}
	@Override
	public void run() {
		try {
			InputStream in = this.incoming.getInputStream();
			OutputStream out = this.incoming.getOutputStream();
			
			Scanner scanner = new Scanner(in);
			PrintWriter printWriter = new PrintWriter(out,true);
			printWriter.println("S->C: Hello ,Enter bye to quit.");
			boolean stop = false;
			while(scanner.hasNextLine() && !stop){
				String str = scanner.nextLine();
				if(str.equals("request object")){
					ObjectOutputStream stream = new ObjectOutputStream(out);
					stream.writeObject(new Student(1, "slieer", "plus"));
				}else if(str.equalsIgnoreCase("bye")){
					printWriter.println("S->C: close connection.");
					stop = true;
				}else{
					printWriter.println("S->C: hi");
				}
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				incoming.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
