package org.slieer.http.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpCli {
	
	public static CloseableHttpClient getHttpClient() {		
		RequestConfig requestConfig = RequestConfig.custom()
		.setConnectTimeout(5000)
		.setConnectionRequestTimeout(5000)
		.setConnectionRequestTimeout(20000)
		.setSocketTimeout(40000)
		.build();
		
		HttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(3, true);
		
		CloseableHttpClient	httpClient = HttpClientBuilder.create()
					.setRetryHandler(handler)
					.setDefaultRequestConfig(requestConfig)
					.build();
		
		return httpClient;
	}			
	
	public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			if (hostname.contains("192.168") || hostname.contains("localhost") || hostname.contains("127.0.0.1")) {
				return true;
			}

			HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
			return hv.verify(hostname, session);
		}
	};
	
	public static CloseableHttpClient getSSLClient() {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.setConnectionRequestTimeout(20000)
				.setSocketTimeout(40000)				
				.build();
		
		HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3, true);
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
		}

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, HOSTNAME_VERIFIER);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", sslsf).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom()				
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig)
				.setRetryHandler(retryHandler)
				.build();

		return httpClient;
	}
	
	
	//X509ExtendedTrustManager m = new X509TrustManagerImpl();
	
}
