package com.net;

import java.net.InetAddress;

public class DomainName {
	public static void main(String[] args) throws Exception {
		// 得到本机名
		InetAddress address1 = InetAddress.getLocalHost();
		System.out.println("本机名： " + address1.getHostName());

		// 直接返回域名
		InetAddress address2 = InetAddress.getByName("www.oracle.com");
		System.out.println("直接得到域名： " + address2.getHostName());

		// 通过DNS查找域名
		InetAddress address3 = InetAddress.getByName("141.146.8.66");
		System.out.println("address3: " + address3); // 域名为空
		System.out.println("address3通过DNS查找域名： " + address3.getHostName());
		
		InetAddress address4 = InetAddress.getByName("www.mobeehome.com");
		System.out.println("mobee： " + address4.getHostAddress());		
	}
	
	
}