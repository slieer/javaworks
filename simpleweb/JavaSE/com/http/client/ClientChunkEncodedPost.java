package com.http.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

/**
 * http://www.cnblogs.com/chinareny2k/archive/2010/03/22/1691624.html
 * Example how to use unbuffered chunk-encoded POST request.
 */
public class ClientChunkEncodedPost {

    public static void main(String[] args) throws Exception {
        if (args.length != 1)  {
            System.out.println("File path not given");
            System.exit(1);
        }
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://localhost:8088/struts2/StringServlet");
        File file = new File(args[0]);
        InputStreamEntity reqEntity = new InputStreamEntity(
                new FileInputStream(file), -1);
        reqEntity.setContentType("binary/octet-stream");
        reqEntity.setChunked(true);
        
        httppost.setEntity(reqEntity);
        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,3000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 3000);//连接超时
        System.out.println("executing request " + httppost.getRequestLine());
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
    
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println("Chunked?: " + resEntity.isChunked());
                InputStream is = resEntity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                try {
                    
                    // do something useful with the response
                    System.out.println(reader.readLine());
                    
                } catch (IOException ex) {
    
                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    throw ex;
                    
                } catch (RuntimeException ex) {
    
                    // In case of an unexpected exception you may want to abort
                    // the HTTP request in order to shut down the underlying 
                    // connection and release it back to the connection manager.
                    httppost.abort();
                    throw ex;
                    
                } finally {
    
                    // Closing the input stream will trigger connection release
                    reader.close();
                    
                }
                
            }
            if (resEntity != null) {
                resEntity.consumeContent();
            }
        } catch (IOException ex) {
            
            // In case of an IOException the connection will be released
            // back to the connection manager automatically
            throw ex;
            
        } catch (RuntimeException ex) {
            
            // In case of an unexpected exception you may want to abort
            // the HTTP request in order to shut down the underlying 
            // connection and release it back to the connection manager.
            httppost.abort();
            throw ex;
            
        } finally {

            // Closing the input stream will trigger connection release
            
        }
        // When HttpClient instance is no longer needed, 
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();        
    }
    
}