package com.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

public class SendMessage2Sina {

	private static final String ACCESS_SECRET = "access_secret";
	private static final String ACCESS_TOKEN = "access_token";
	private static boolean HAVE_ACCESS_TOKEN = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", "4212139527");
		System.setProperty("weibo4j.oauth.consumerSecret",
				"e20e613826de4d084b279a0236c167de");
//		System.setProperty("weibo4j.debug", "true");
		Weibo weibo = new Weibo();
		AccessToken accessToken = null;
		
		String fileName = "c:\\sina_token.properties";
		File f = new File(fileName);
		HAVE_ACCESS_TOKEN = f.exists();
		
		if (!HAVE_ACCESS_TOKEN) {
			String backUrl = "http://www.javaeye.com/";
			RequestToken requestToken;
			try {
				requestToken = weibo.getOAuthRequestToken(backUrl);
				System.out.println("Got request token...");
				System.out.println("Request token: " + requestToken.getToken());
				System.out.println("Request token secret: "
						+ requestToken.getTokenSecret());
				
				System.out.println("Open the following url in a browser \n http://api.t.sina.com.cn/oauth/authorize?oauth_token=" + requestToken.getToken());
				
				System.out.println("please input verifier:");

				String verifier = readLine();
				
				accessToken = weibo.getOAuthAccessToken(
						requestToken.getToken(), requestToken.getTokenSecret(),
						verifier);
				
				
				
				OutputStream out=new FileOutputStream(fileName);
				Properties p = new Properties();
				p.setProperty(ACCESS_TOKEN, accessToken.getToken());
				p.setProperty(ACCESS_SECRET, accessToken.getTokenSecret());
				p.store(out, "sina access token");
				out.flush();
				out.close();
			} catch (WeiboException e) {
				// 太奇怪了,一个
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			InputStream in;
			try {
				in = new FileInputStream(fileName);
				PropertyResourceBundle bundle = new PropertyResourceBundle(in);
				accessToken = new AccessToken(bundle.getString(ACCESS_TOKEN), bundle.getString(ACCESS_SECRET));
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());
		
		try {
			System.out.println("Input message to sina:");
			String message = readLine();
			while (!message.trim().equals("exit")) {
				weibo.updateStatus(message);
				message = readLine();
			}
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private static String readLine() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        byte[] bs = reader.readLine().getBytes("gbk");
		String getStr = new String(bs);
		System.out.println(getStr);
		return getStr;
	}

}
