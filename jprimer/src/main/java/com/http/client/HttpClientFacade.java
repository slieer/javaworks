package com.http.client;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

/**
 * http://hc.apache.org/httpcomponents-client-ga/tutorial/html/httpagent.html
 * 
 * @author me
 */
public class HttpClientFacade {
	public static void main(String[] args) {

	}

	public static void facade() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					// Keep connections alive 5 seconds if a keep-alive value
					// has not be explicitly set by the server
					keepAlive = 5000;
				}
				return keepAlive;
			}
		});

		httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.setHeader(HTTP.USER_AGENT, "My-own-client");
			}
		});

	}
}
