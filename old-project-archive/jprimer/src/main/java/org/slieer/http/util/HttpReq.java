package org.slieer.http.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HttpReq {
	private static final String FORM = "application/x-www-form-urlencoded";
	private static final String JSON = "application/json";
	private static final String TEXT = "text/plain";

	private  CloseableHttpClient  httpClient = null;
	
	public static HttpReq https(){
		return new HttpReq(HttpCli.getSSLClient());
	}

	public static HttpReq http(){
		return new HttpReq(HttpCli.getHttpClient());
	}
	
	private HttpReq(CloseableHttpClient  httpClient){
		this.httpClient = httpClient;
	}
	
	/**post json, retrun json*/
	public ObjectNode postReqResult(String url, Map<String,String> postKeyVal) throws IOException, ClientProtocolException {
		String jsonStr = JsonUtils.toJson(postKeyVal);
		HttpEntity entity = new StringEntity(jsonStr, ContentType.create(JSON, "utf-8"));
		ObjectNode obj = postRequest(url, entity);
		return obj;
	}
	/**post json, retrun json*/
	public ObjectNode postReqResult(String url, JsonNode postKeyVal) throws IOException, ClientProtocolException {
		HttpEntity entity = null;
		if(postKeyVal != null){
			String jsonStr = JsonUtils.toJson(postKeyVal);			
			entity = new StringEntity(jsonStr, ContentType.create(JSON, "utf-8"));
		}
		ObjectNode obj = postRequest(url, entity);
		return obj;
	}

	public ArrayNode postReqArr(String url, JsonNode postKeyVal) throws IOException, ClientProtocolException {
		HttpEntity entity = null;
		if(postKeyVal != null){
			String jsonStr = JsonUtils.toJson(postKeyVal);			
			entity = new StringEntity(jsonStr, ContentType.create(JSON, "utf-8"));
		}
		ArrayNode obj = postRequestReturnArr(url, entity);
		return obj;
	}

	/**post json, retrun String*/
	public String postReqResultStr(String url, Map<String,String> postKeyVal) throws IOException, ClientProtocolException {
		String jsonStr = JsonUtils.toJson(postKeyVal);
//		System.out.println("jsonStr:" + jsonStr);
		HttpEntity entity = new StringEntity(jsonStr, ContentType.create(JSON, "utf-8"));
		String result = postRequestStr(url, entity);
		return result;
	}
	
	
	public ObjectNode postReqResult(String url, String postData) throws IOException, ClientProtocolException {
		HttpEntity entity = new StringEntity(postData, ContentType.create(JSON, "utf-8"));
		ObjectNode obj = postRequest(url, entity);
//		System.out.println(obj);
		return obj;
	}
	
	public ObjectNode postTextReqResult(String url, String postData) throws IOException, ClientProtocolException {
		HttpEntity entity = new StringEntity(postData, ContentType.create(TEXT, "utf-8"));
		return postRequest(url, entity);
//		System.out.println(obj);
	}
	
	public ObjectNode postFormReqResult(String url, String postData) throws IOException, ClientProtocolException {
		HttpEntity entity = new StringEntity(postData, ContentType.create(FORM, "utf-8"));		
		ObjectNode obj = postRequest(url, entity);
//		System.out.println(obj);
		return obj;
	}

	public ObjectNode getRequest(String url) throws IOException, ClientProtocolException {
		HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);  
            
        HttpEntity httpEntity= response.getEntity();               
//        System.out.println("StatusLine: " + response.getStatusLine());
        
//		Header[] header = response.getAllHeaders();
//		for(int i = 0; i < header.length; i++){
//			System.out.println(header[i].getName() + ":" + header[i].getValue());
//		}
        
        String content = EntityUtils.toString(httpEntity, "UTF-8");
//        System.out.println(content);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//        	System.out.println(content);
        	
        	return JsonUtils.parse(content, ObjectNode.class);
        }else{
        	System.err.println(content);
        }
        return null;
	}
		
	/**
	 * put request
	 * @param url
	 * @param entity
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public ObjectNode putTextRequest(String url, String putData) throws IOException, ClientProtocolException {		
		HttpEntity entity = new StringEntity(putData, ContentType.create(TEXT, "utf-8"));		
		HttpPut method = new HttpPut(url);
		method.setEntity(entity);
        HttpResponse response = httpClient.execute(method);  
            
        HttpEntity httpEntity= response.getEntity();               
        System.out.println("StatusLine: " + response.getStatusLine());
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        	String content = EntityUtils.toString(httpEntity, "UTF-8");
//        	System.out.println(content);
        	return JsonUtils.parse(content, ObjectNode.class);
        }
        return null;
	}	
	
	public ObjectNode putJsonRequest(String url, String putData) throws IOException, ClientProtocolException {		
		HttpEntity entity = new StringEntity(putData, ContentType.create(JSON, "utf-8"));		
		HttpPut method = new HttpPut(url);
		method.setEntity(entity);
        HttpResponse response = httpClient.execute(method);  
        
        HttpEntity httpEntity= response.getEntity();               
        System.out.println("StatusLine: " + response.getStatusLine());
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        	String content = EntityUtils.toString(httpEntity, "UTF-8");
//        	System.out.println(content);
        	return JsonUtils.parse(content, ObjectNode.class);
        }
        return null;
	}	
	
	public ObjectNode deleteJsonRequest(String url){
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			HttpResponse response = httpClient.execute(httpDelete);
			HttpEntity httpEntity = response.getEntity();
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String content = EntityUtils.toString(httpEntity,"UTF-8");
//				System.out.println(content);
				return JsonUtils.parse(content, ObjectNode.class);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
		
	/**
	 * post
	 * @param url
	 * @param entity
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public ObjectNode postRequest(String url, HttpEntity entity) throws IOException, ClientProtocolException {
		String content = postRequestStr(url, entity);
//		System.out.println(content);
        return StringUtils.isNotBlank(content) ? JsonUtils.parse(content, ObjectNode.class) : null;
	}

	public ArrayNode postRequestReturnArr(String url, HttpEntity entity) throws IOException, ClientProtocolException {
		String content = postRequestStr(url, entity);
        return content != null ? JsonUtils.parse(content, ArrayNode.class) : null;
	}	
	
	public String postRequestStr(String url, HttpEntity entity) throws IOException, ClientProtocolException {
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setEntity(entity);		
        HttpResponse response = httpClient.execute(httpPost);  
            
        HttpEntity httpEntity= response.getEntity();               
//        System.out.println("StatusLine: " + response.getStatusLine());
        String content = EntityUtils.toString(httpEntity, "UTF-8");
        int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != HttpStatus.SC_OK){
        	System.err.println("statusCode:" + statusCode + "," + content);
        	return null;
        }
		return content;
	}	
	
}
