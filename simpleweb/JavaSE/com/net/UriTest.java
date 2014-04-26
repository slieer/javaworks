package com.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

public class UriTest {
    @Test
    public void urlToURI(){
        try {
            URL url = new URL("http://baidu.com?wd=%E6%96%8C%20%20%2B");
            URI uri = url.toURI();
            
            JSONObject json = JSONObject.fromObject(uri);
            System.out.println(json.toString());
            
            
            URI newUri = new URI("http", null,"www.mobee.com",80,null,null,null);
            
            System.out.println(newUri.toURL().toString());
            json = JSONObject.fromObject(newUri);
            System.out.println(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void apacheCodec(){
        URLCodec u = new URLCodec();
        try {
            String urlStr = u.encode("翟+");
            System.out.println(urlStr);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <li>基本使用方法, 不能处理空格、多字节字符.
     * <li>输出结果： <pre>{
        "absolute": true,
        "authority": "www.cnblogs.com",
        "fragment": "",
        "host": "www.cnblogs.com",
        "opaque": false,
        "path": "/springside5/archive/userInfo.action",
        "port": -1,
        "query": "name=x&sex=xx&adr=xxx",
        "rawAuthority": "www.cnblogs.com",
        "rawFragment": "",
        "rawPath": "/springside5/archive/userInfo.action",
        "rawQuery": "name=x&sex=xx&adr=xxx",
        "rawSchemeSpecificPart": "//www.cnblogs.com/springside5/archive/userInfo.action?name=x&sex=xx&adr=xxx",
        "rawUserInfo": "",
        "scheme": "http",
        "schemeSpecificPart": "//www.cnblogs.com/springside5/archive/userInfo.action?name=x&sex=xx&adr=xxx",
        "userInfo": ""
    }</pre>*/
    
    @Test
    public void baisc() {
        try {
            URI userUri = new URI("http://www.cnblogs.com/springside5/archive/userInfo.action?name=x&sex=xx&adr=翟xx");
            JSONObject userJson = JSONObject.fromObject(userUri);
            System.out.println(userJson.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
