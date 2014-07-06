package com.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpclientConnMgmt {
	public static void main(String[] args) {
		try {
			f();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void f() throws Exception {
		Scheme scheme = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(scheme);

		ClientConnectionManager cm = new ThreadSafeClientConnManager(schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm);

		// URIs to perform GETs on
		String[] urisToGet = { "http://news.baidu.com/", "http://download.oracle.com/javase/1.5.0/docs/guide/security/jsse/JSSERefGuide.html",
				"http://www.google.com.hk/", "http://scholar.google.com.hk/schhp?hl=zh-CN&tab=ws" };

		// create a thread for each URI
		GetThread[] threads = new GetThread[urisToGet.length];
		for (int i = 0; i < threads.length; i++) {
			HttpGet httpget = new HttpGet(urisToGet[i]);
			threads[i] = new GetThread(httpClient, httpget);
		}

		// start the threads
		for (int j = 0; j < threads.length; j++) {
			threads[j].start();
		}

		// join the threads
		for (int j1 = 0; j1 < threads.length; j1++) {
			threads[j1].join();
		}
	}

	static class GetThread extends Thread {

		private final HttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;

		public GetThread(HttpClient httpClient, HttpGet httpget) {
			this.httpClient = httpClient;
			this.context = new BasicHttpContext();
			this.httpget = httpget;
		}

		@Override
		public void run() {
			try {
				HttpResponse response = this.httpClient.execute(this.httpget, this.context);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// do something useful with the entity
				}
				// ensure the connection gets released to the manager
				EntityUtils.consume(entity);
			} catch (Exception ex) {
				this.httpget.abort();
			}
		}

	}

}
