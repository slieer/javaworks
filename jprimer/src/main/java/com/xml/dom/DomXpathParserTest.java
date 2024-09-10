package com.xml.dom;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *http://www.ibm.com/developerworks/cn/xml/x-javaxpathapi.html 
 */
public class DomXpathParserTest {
	public static String file = "JavaSE/com/xml/dom/book.xml";
	public static void main(String[] args) {
		try {
			//domRead();
			xpathRead();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static void xpathRead() throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(file);	

    XPath xpath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
    
    Object result = expr.evaluate(doc, XPathConstants.NODESET);
    
    NodeList nodes = (NodeList) result;
    for (int i = 0; i < nodes.getLength(); i++) {
        System.out.println(nodes.item(i).getNodeValue()); 
    }
	}
	
	public static void domRead() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(file); // 存放该xml文件的路径;

		ArrayList<Node> result = new ArrayList<Node>();
		NodeList books = document.getElementsByTagName("book");
		for (int i = 0; i < books.getLength(); i++) {
			Element book = (Element) books.item(i);
			NodeList authors = book.getElementsByTagName("author");
			boolean stephenson = false;
			for (int j = 0; j < authors.getLength(); j++) {
				Element author = (Element) authors.item(j);
				NodeList children = author.getChildNodes();
				StringBuffer sb = new StringBuffer();
				for (int k = 0; k < children.getLength(); k++) {
					Node child = children.item(k);
					// really should to do this recursively
					if (child.getNodeType() == Node.TEXT_NODE) {
						sb.append(child.getNodeValue());
					}
				}
				if (sb.toString().equals("Neal Stephenson")) {
					stephenson = true;
					break;
				}
			}

			if (stephenson) {
				NodeList titles = book.getElementsByTagName("title");
				for (int j = 0; j < titles.getLength(); j++) {
					System.out.println(titles.item(0).toString());
					result.add(titles.item(j));
				}
			}
		}
	}
}
