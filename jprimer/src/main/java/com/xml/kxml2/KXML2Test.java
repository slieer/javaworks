package com.xml.kxml2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class KXML2Test {
    @Test
    public void readTest() throws Exception {
        URL url = this.getClass().getResource("/");
        String xmlFile = url.getPath() + "com/xml/unformattedXML.xml";
        File file = new File(xmlFile);

        InputStream stream = new FileInputStream(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(stream, null);
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
            case XmlPullParser.START_DOCUMENT:
                System.out.println(parser.getText());
                break;
            case XmlPullParser.END_DOCUMENT:

                break;
            case XmlPullParser.START_TAG:

                break;
            }
        }
    }

    @Test
    public void readNetTest() throws Exception {
        URL u = new URL(
                "http://192.168.52.21/dy-ottdevice/device/ActiveDevice.action?deviceNO=001E4FED89041252q2nE8");
        URLConnection conn = u.openConnection();
        InputStream in = conn.getInputStream();

        parseXML1(in);
    }

    private static String status, msg, username, pwd, token;

    private static void parseXML1(InputStream is) throws Exception{
        KXmlParser parser = new KXmlParser();

        String name = "";
        parser.setInput(is, HTTP.UTF_8);
        
        parser.nextTag();
        while (XmlPullParser.START_TAG == parser.nextTag()) {
            name = parser.getName();
            if (null != name && name.length() > 0) {
                name = name.toLowerCase();
                String value = parser.nextText();

                if ("status".equals(name)) {
                    status = value;
                } else if ("token".equals(name)) {
                    token = value;
                } else {
                   System.out.println("[AutoRegisterTask.parseXml] Unsupported value: " + name+"="+value);
                }
            }
            System.out.println("status, passport:" + status + "," + token);
        }        
    }
    
    
    private static void parseXML(InputStream is) {
        String currentTag = null;
        XmlPullParserFactory factory;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, HTTP.UTF_8);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    currentTag = xpp.getName();
                } else if (eventType == XmlPullParser.END_TAG) {
                    currentTag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (currentTag != null && xpp.getText() != null) {
                        System.out.println(currentTag + ":" + xpp.getText());
                    }
                }
                eventType = xpp.next();
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("status, passport:" + status + "," + token);
    }
    
    public static class APK {
        String id;
        String installType;
        String packageName;

        @Override
        public String toString() {
            return "APK [opType=" + installType + ", appPackage=" + packageName
                    + "]";
        }
    }

    private static List<APK> parseUpdataAPKXML(InputStream is)
            throws Exception {
        List<APK> list = null;

        XmlPullParserFactory pullParserFactory = XmlPullParserFactory
                .newInstance();
        XmlPullParser xmlPullParser = pullParserFactory.newPullParser();
        xmlPullParser.setInput(is, "UTF-8");
        // 开始
        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = xmlPullParser.getName();
            APK apk = null;
            switch (eventType) {
            // 文档开始
            case XmlPullParser.START_DOCUMENT:
                list = new ArrayList<APK>();
                break;
            // 开始节点
            case XmlPullParser.START_TAG:
                // 判断如果其实节点为student
                String text = safeNextText(xmlPullParser);
                //log.i(nodeName + "=" + text);
                if ("widget".equals(nodeName)) {
                    // 实例化student对象
                    apk = new APK();
                } else if ("id".equals(nodeName)) {

                    apk.id = text;
                } else if ("installType".equals(nodeName)) {

                    apk.installType = text;
                } else if ("packageName".equals(nodeName)) {

                    apk.packageName = text;
                }
                break;
            // 结束节点
            case XmlPullParser.END_TAG:
                if ("widget".equals(nodeName)) {
                    list.add(apk);
                    apk = null;
                }
                break;
            default:
                break;
            }
            eventType = xmlPullParser.next();
        }
        return list;
    }

    private static String safeNextText(XmlPullParser parser)  
             throws XmlPullParserException, IOException {  
         String result = parser.nextText();  
         if (parser.getEventType() != XmlPullParser.END_TAG) {  
             parser.nextTag();  
         }  
         return result;  
     }  
    
}
