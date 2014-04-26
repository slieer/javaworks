package com.net;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @author yoUng
 * 
 * @description 发送http请求
 * 
 * @filename HttpUtil.java
 * 
 * @time 2011-6-15 下午05:26:36
 * 
 * @version 1.0
 */
public class HttpPostTest {
    
    //http://192.168.51.230/dy-ottdevice/device/ActiveDevice.action?deviceNO=3001
    //<token>Yh7LpdxCfyD5KegZvqSiou38DtT1QHpOpkMKpWID7dY*</token>
    public static void main(String[] args) {
        String url = "http://192.168.51.230/" +
        		"store/widget/service.action?" +
        		"op=UPGRADEWG&start=0&count=10&sdkLevel=15" +
        		"&pkname=com.shequ.s011.medicalview&version=1.3&deviceID=3001&borqspassport=Yh7LpdxCfyD5KegZvqSiou38DtT1QHpOpkMKpWID7dY*";
        http(url, null);
    }
    
    
    public static String http(String url, Map<String, String> params) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        System.out.println("send_url:" + url);
        System.out.println("send_data:" + sb.toString());
        // 尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(
                    con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con
            .getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
