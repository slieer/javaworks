package com.http.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


public class Fundamentals {
	public static void main(String[] args) {
		String phoneNo = "8613714532530"; 
		String token = "YPRG0MWas7e6lM6sQ9CL4mxbtGk49jB-SNgtULIamd1SwAeSqwV0bw**";
		
		String url = constructUrl(phoneNo, token);
		try {
			File file = new File("JavaSE/com/http/example.xml");
			//put(url,file);
			get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  # pHJlE_jdISlHeopL0RSI_QZ8T7ZzPcTXyKRTRUHHjMNSwAeSqwV0bw**  8613828724021<br>
		# eE60nQE4So54sNTD59vl-JzFNkMl1lxDqbgptVFd1-FSwAeSqwV0bw**  8613923406916<br>
		
		# DDcs3x7JwQQwqvOT751dhyp3s2od75lFbuwRL9UfCpJSwAeSqwV0bw**  8613480783139<br>
		
		# _MfVVlhCuyfw-nlZpt6AqncsOzPC3jjYQ3Pw6wyJoAZSwAeSqwV0bw**  8615989380390<br>
		
		# YPRG0MWas7e6lM6sQ9CL4mxbtGk49jB-SNgtULIamd1SwAeSqwV0bw**  8613714532530<br>

	 * @param phoneNo
	 * @param token
	 * @return
	 */
	public static String constructUrl(String phoneNo, String token) {
		String url = "http://localhost:8080/xcap-root/contacts/{0}/{1}/index";
		MessageFormat form = new MessageFormat(url);
		Object[] args = { phoneNo, token };
		return form.format(args);
	}

	public static void get(String url){
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httppost = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            
            Scanner s = new Scanner(resEntity.getContent());
            while(s.hasNextLine()){
            	System.out.println(s.nextLine());
            }
/*          if(response.getStatusLine().getStatusCode() == 200){
            }
*/
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
	public static void put(String url, File file) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();

        HttpPut httppost = new HttpPut(url);
        InputStreamEntity reqEntity = new InputStreamEntity(
                new FileInputStream(file), -1);
        reqEntity.setContentType("text/xml");
        reqEntity.setChunked(true);
        
        httppost.setEntity(reqEntity);
        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,3000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 3000);//连接超时
        System.out.println("executing request " + httppost.getRequestLine());
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            
            System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
            Scanner s = new Scanner(resEntity.getContent());
            while(s.hasNextLine()){
            	System.out.println(s.nextLine());
            }
/*            if(response.getStatusLine().getStatusCode() == 200){
            }
*/
        }catch(Exception e){
        	e.printStackTrace();
        } 
	}
	
	@Test
	public void getBasic(){
		String urlStr = "http://www.wllxy.net/article.aspx?id=107";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("GET");			
			httpConn.setDoInput(true);
			InputStream in = httpConn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"gb2312"));

            Scanner s = new Scanner(br);
            while(s.hasNextLine()){
            	String ori = s.nextLine();
            	//String result = new String(ori.getBytes( "gb2312"));//不行
            	System.out.println(ori);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void get(){		
		String url = "http://www.wllxy.net/article.aspx?id=107";
		//url = "http://www.baidu.com";
		//url = "http://www.baidu.com";
		
		
		Scheme scheme = new Scheme("http",80, PlainSocketFactory.getSocketFactory());
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(scheme);
		ClientConnectionManager cm = new ThreadSafeClientConnManager();
		
		HttpClient httpclient = new DefaultHttpClient(cm);
        HttpGet get = new HttpGet(url);
          
        	
        //get.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        get.setHeader("User-Agent", "Mozilla/5.0(compatible;MSIE 9.0;Windows NT 6.1;Trident/5.0;BOIE9;ZHCN)");
        get.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        //get.setHeader("Accept-Charset", "gb2312");
        //get.setHeader("Accept-Charset", "utf-8");
        //get.setHeader("Accept-Charset", "gbk");
        //get.setHeader("Content-Type", "text/html;charset=gb2312");
        //httppost.setHeader("Accept-Encoding","gzip,deflate");

        try {
            HttpResponse response = httpclient.execute(get);
            HttpEntity resEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(resEntity.getContent(),"gb2312"));

            Scanner s = new Scanner(br);
            while(s.hasNextLine()){
            	String ori = s.nextLine();
            	//String result = new String(ori.getBytes( "gb2312"));//不行
            	System.out.println(ori);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
}  

