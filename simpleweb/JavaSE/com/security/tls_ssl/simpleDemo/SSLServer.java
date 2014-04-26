package com.security.tls_ssl.simpleDemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
   
public class SSLServer {    
    private static int port = 50003;    
    private static SSLServerSocket server;
    public static String encode = "utf-8";
    
    public static void main(String args[]) {    
        try {    
            initSSLServerSocket();    
            System.out.println("服务器在端口 [" + port + "] 等待连接...");    
            while (true) {    
                SSLSocket socket = (SSLSocket) server.accept();    
                new CreateThread(socket);    
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
   
    public static void initSSLServerSocket() {    
        try {    
            /** 要使用的证书名 **/   
            String cert = "JavaSE/com/security/tls_ssl/simpleDemo/key.cert";    
            /** 要使用的证书密码 **/   
            char certPass[] = "123456".toCharArray();    
            /** 证书别称所使用的主要密码 **/   
            char certAliaMainPass[] = "123456".toCharArray();    
            /** 创建JKS密钥库 **/   
            KeyStore keyStore = KeyStore.getInstance("JKS");    
            keyStore.load(new FileInputStream(cert), certPass);    
            /** 创建管理JKS密钥库的X.509密钥管理器 **/   
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");    
            keyManagerFactory.init(keyStore, certAliaMainPass);    
            SSLContext sslContext = SSLContext.getInstance("TLSV1");  //采用TLS v1.
            
            /** 想使用SSL时，更改成如下,注释部分 **/   
            //SSLContext sslContext = SSLContext.getInstance("SSLV3");    
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);    
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();    
            server = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);    
        } catch (Exception e) {    
            e.printStackTrace();    
        }   
    }    
}    
   
class CreateThread extends Thread {    
    private static BufferedReader in;    
    private static PrintWriter out;    
    private static Socket s;    
   
    public CreateThread(Socket socket) {    
        try {    
            s = socket;    
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), SSLServer.encode));    
            out = new PrintWriter(s.getOutputStream(), true);    
            start();    
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }    
   
    public void run() {    
        try {    
            String msg = in.readLine();    
            System.out.println("接收到: " + msg);    
            out.write("服务器接收到的信息是: " + msg);    
            out.flush();    
            s.close();    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }    
}   
