package com.http.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpPutTest {
	String url = "http://localhost:8080/simpleweb/PutServlet";
	
	/**
	 * Http client post方式
	*/
	public void httpClientPostTest(String[] args) throws Exception {
		HttpPost httppost = new HttpPost(url);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pwd", "2544"));
		httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = new DefaultHttpClient().execute(httppost);
		if (response.getStatusLine().getStatusCode() == 200) {// 如果状态码为200,就是正常返回
			String result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
			// 打印输出
		}
	}	
	
	/**
	 * javase api http post Test
	 * @throws Exception
	 */
	@Test
	public void put() throws Exception {
		String file = "E:/BorqsWorkspace/workspace/simpleweb/JavaSE/com/http/client/HttpclientTutorial.java";
		HttpPut httpPut = new HttpPut(url);
		InputStreamEntity reqEntity = new InputStreamEntity(
				new FileInputStream(file), -1);
		reqEntity.setContentType("text/xml");
		reqEntity.setChunked(true);

		httpPut.setEntity(reqEntity);

		HttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
		params.setIntParameter(HttpConnectionParams.SO_TIMEOUT, 3000); // 超时设置
		params.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 3000);// 连接超时
		System.out.println("executing request " + httpPut.getRequestLine());
		response(httpclient, httpPut);
	}

	private static void response(HttpClient httpclient, HttpRequestBase httpRes) {
		try {
			HttpResponse response = httpclient.execute(httpRes);
			HttpEntity resEntity = response.getEntity();

			Header[] headers = response.getAllHeaders();
			System.out.println("----------header-------------");
			System.out.println("Status-Code:"
					+ response.getStatusLine().getStatusCode());
			for (int i = 0; i < headers.length; i++) {
				Header header = headers[i];
				System.out.println(header);
			}

			System.out.println("----------content-------------");
			Scanner s = new Scanner(resEntity.getContent());
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
			/*
			 * if(response.getStatusLine().getStatusCode() == 200){}
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

}
