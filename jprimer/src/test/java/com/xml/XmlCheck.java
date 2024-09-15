package com.xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * 判断xml 中是否有书写错误
 * @author me
 *
 */
public class XmlCheck {
	static String xmlFile = "JavaSE/com/xml/book.xml";

	static String badXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
		+ "<XMLDataRoot name=\"百度文库\" type=\"computer\">" 
		+ "<title>XML轻松学习手册</title>"
		+ "<author>张三</author>" 
		+ "<email>slieer@gmail.com</email>";// +
	
	static String goodXmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
		+ "<XMLDataRoot name=\"百度文库\" type=\"computer\">" 
		+ 	"<title>XML轻松学习手册</title>"
		+ 	"<author>张三</author>" 
		+ 	"<email>slieer@gmail.com</email>"
		+ 	"<date>2011-9-20</date>" 
		+ "</XMLDataRoot>";
	

	public static void main(String[] args) {
		InputSource input = new InputSource(new StringReader(goodXmlString));
		InputSource badInput = new InputSource(new StringReader(badXmlString));
		//dom(input);
		sax(badInput);
	}

	public static void sax(InputSource input) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		XMLReader reader;
		try {
			reader = spf.newSAXParser().getXMLReader();
			reader.parse(input);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void jdom(){
	}
	
	public static void dom4j(){
	}
	
	public static void dom(InputSource input) {
		DocumentBuilder parser;
		try {
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// Document document = parser.parse(new File(xmlFile));
			Document document = parser.parse(input);
			
			System.out.println("------------------");
			Document document1 = parser.parse(new InputSource(new StringReader(badXmlString)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
