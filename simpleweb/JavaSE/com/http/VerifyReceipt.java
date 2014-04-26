package com.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;

public class VerifyReceipt {
	
	public int verifyReceipt( byte[] receipt) throws Exception {
	       int status = -1;
	 
	        //This is the URL of the REST webservice in iTunes App Store
	        java.net.URL url = new java.net.URL("https://buy.itunes.apple.com/verifyReceipt");
	 
	        //make connection, use post mode
	        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);
	        connection.setAllowUserInteraction(false);
	 
	        //Encode the binary receipt data into Base 64
	        //Here I'm using org.apache.commons.codec.binary.Base64 as an encoder, since commons-codec is already in Grails classpath
	        Base64 encoder = new Base64();
	        //encoder.decode(pArray);
	        String encodedReceipt = new String(encoder.encode(receipt));
	 
	        //Create a JSON query object
	        //Here I'm using Grails' org.codehaus.groovy.grails.web.json.JSONObject
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("receipt-data", encodedReceipt);
	        JSONObject jsonObject = JSONObject.fromObject(map);
	 
	        //Write the JSON query object to the connection output stream
	        PrintStream ps = new PrintStream(connection.getOutputStream());
	        ps.print(jsonObject.toString());
	        ps.close();
	 
	        //Call the service
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        //Extract response
	        String str;
	        StringBuffer sb = new StringBuffer();
	        while ((str = br.readLine()) != null) {
	            sb.append(str);
	            sb.append("/n");
	        }
	        br.close();
	        String response = sb.toString();
	 
	        //Deserialize response
	        JSONObject result = JSONObject.fromObject((response));
	        status = result.getInt("status");
	        if (status == 0) {
	            //provide content
	        } else {
	            //signal error, throw an exception, do your stuff honey!
	        }
	 
	        return status ;
	 

	    }

}
