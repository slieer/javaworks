package com.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;

public class HttpPost {
	@Test
	public void javaSEAPIHPPTPostTest(String[] args) {
		String url = "http://localhost:8080/simpleweb/PutServlet?name=zhaixiaobin&sex=man"; 
		String data = "address=china&company=borqs&job=coder[{a:\"b\",}]";
		
		HttpURLConnection httpConn = null;
		try {
			URL reqUrl = new URL(url);
			httpConn = (HttpURLConnection)reqUrl.openConnection();
			
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			
			//String encodeData = URLEncoder.encode(data,"UTF-8");
			OutputStream out = httpConn.getOutputStream();
			out.write(data.getBytes("UTF-8"));
			out.close();

			Scanner scanner = new Scanner(httpConn.getInputStream());
			StringBuilder returnInfo = new StringBuilder();
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				returnInfo.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
