package com.http.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.resource.spi.IllegalStateException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/***
 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
 * #d4e37
 * 
 * @author me
 */

public class HttpclientTutorial {
	// google china main page.
	static String URL = "http://www.google.com.hk/webhp?hl=zh-CN&sourceid=cnhp";
	//static String URL = "http://www.baidu.com/home";
	
	/**
	 *<li>初级的client</li> 
	 */
	static void client(HttpUriRequest request) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
				
		HttpResponse response = httpclient.execute(request);		
		
		int status = response.getStatusLine().getStatusCode();
		if(status != HttpStatus.SC_OK){
			throw new IllegalStateException("status code is " + status);
		}
		
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
			if (entity != null) {
		    entity = new BufferedHttpEntity(entity);
		  }
			String charset = getResponseCharset(response);
			
			output(entity, charset);
		}
	}

	/**
	 * <li>default charset is UTF-8</li>
	 * <li>or server response header charset</li>
	 */
	public static String getResponseCharset(HttpResponse response) {
		//String charset1 = EntityUtils.getContentCharSet(entity);
		
		String charset = "UTF-8";
		Header[] headers = response.getHeaders("Content-Type");  //text/html; charset=GB2312
		if(headers != null && headers.length > 0){
			Header header = headers[0];
			HeaderElement[] el = header.getElements();
			NameValuePair[] pairs = el[0].getParameters();
			
			if(pairs.length != 0){
				charset = pairs[0].getValue();				
			}
		}
		
		return charset;
	}

	public static String output(HttpEntity entity, String charset) throws IOException {
		InputStream instream = entity.getContent();
		Scanner scanner = new Scanner(instream,charset);
		StringBuilder result = new StringBuilder();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(line);
			result.append(line);
		}
		instream.close();
		return result.toString();
	}
		
	@Test
	public void simpleGetRequest() throws Exception {
		HttpGet httpget = new HttpGet(URL);
		client(httpget);
	}
	
	static void simpleUriGetRequest() throws Exception {
		URI uri = URIUtils.createURI("http", "www.google.com", -1, "/search", "q=httpclient&btnG=Google+Search&aq=f&oq=", null);
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());

		client(httpget);
	}

	static void simpleSetParamGetRequest() throws Exception {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("q", "httpclient"));
		qparams.add(new BasicNameValuePair("btnG", "Google Search"));
		qparams.add(new BasicNameValuePair("aq", "f"));
		qparams.add(new BasicNameValuePair("oq", null));
		URI uri = URIUtils.createURI("http", "www.google.com", -1, "/search", URLEncodedUtils.format(qparams, "UTF-8"), null);
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());

		client(httpget);

	}
	
	static void file() throws Exception {
		File file = new File("somefile.txt");
		FileEntity entity = new FileEntity(file, "text/plain; charset=\"UTF-8\"");
		HttpPost httppost = new HttpPost("http://localhost/action.do");

		httppost.setEntity(entity);
		client(httppost);
	}
	
	
	static void formRequest() throws Exception {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("param1", "value1"));
		formparams.add(new BasicNameValuePair("param2", "value2"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "gb2312");
		HttpPost httppost = new HttpPost("http://localhost/handler.do");
		httppost.setEntity(entity);
		
		client(httppost);
	}
	
	
	static void responseHandlers() throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://www.baidu.com");

		ResponseHandler<String> handler = new ResponseHandler<String>() {
		    public String handleResponse(
		            HttpResponse response) throws ClientProtocolException, IOException {
		        HttpEntity entity = response.getEntity();
		        if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		        	//String charset = getResponseCharset(response);
		        	String charset1 = EntityUtils.getContentCharSet(entity);
		           return output(entity, charset1);
		        } else {
		            return null;
		        }
		    }
		};
		String response = httpclient.execute(httpget, handler);
		System.out.println(response);
	}
}
