package com.xml.dom;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlStringSaxParser {
	static String xml = "<contact id=\"890\"><method>13823514039</method><contactName>杨威</contactName><userId>13</userId><createDate>2011-09-27 05:26:52</createDate></contact>";

	public static void main(String[] args) {
		try {
			System.out.println(get2(xml,"method"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String get2(String xmlText,String tagName) throws Exception {
		class Handler extends DefaultHandler {
			private String tagName;
			private String value;

			private String elementName;

			Handler(String elementName){
				this.elementName = elementName;
			}		
			
			public void startElement(String uri, String localName, String qName,
					Attributes atts) throws SAXException {
				tagName = qName;
			}

			public void characters(char[] ch, int start, int length)
					throws SAXException {
				String valueTemp = String.valueOf(ch);
				valueTemp = valueTemp.substring(start, start + length);

				if (tagName.equals(elementName) && value == null) {
					this.value = valueTemp;
				}
			}
			
			public String getValue(){
				return value;
			}
		}		
		
		StringReader stringReader = new StringReader(xmlText);
		InputSource source = new InputSource(stringReader);

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		saxParser = spf.newSAXParser();

		Handler h = new Handler(tagName);
		saxParser.parse(source, h);
		stringReader.close();
		return h.getValue();
	}

	static void get1() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource input = new InputSource(new StringReader(xml));
			Document document = db.parse(input); // 存放该xml文件的路径;

			NodeList list = document.getChildNodes();
			NodeList nodeList = document.getElementsByTagName("method");
			if (nodeList.getLength() > 0) {
				String resultXml = nodeList.item(0).toString();

				System.out.println(resultXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
