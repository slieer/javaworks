package com.http;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLValidator {
	static String xsd = "E:/jboss-5.1.0.GA-jdk6/jboss-5.1.0.GA/server/default/tmp/4sg02c-mqxk54-gtxn6gvd-1-gty56r37-a5/xcap-root.war/WEB-INF/classes/com/xcap/web/xmlschema/contacts.xsd";
	static String xml = "JavaSE/com/http/example.xml";

	public static void main(String[] args) {

		try {
			//boolean re = xmlValidator(xml, xsd, true);
			//System.out.println(re);
			xpath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static void xpath(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true); // never forget this!
	    DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xml);	
			
			NodeList list = doc.getElementsByTagName("contact");
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			//book[author='Neal Stephenson']/title/text()
			XPathExpression expr = xpath.compile("//contacts");
			
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeValue()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param xmlFilePath
	 * @param schemaFilePath
	 * @param xmlFileOrXmlText
	 *            true xml file; false xml text.
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static boolean xmlValidator(String xml, String schemaFilePath,
			boolean xmlFileOrXmlText) throws SAXException, IOException {
		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");

		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		File schemaLocation = new File(schemaFilePath);
		Schema schema = factory.newSchema(schemaLocation);

		// 3. Get a validator from the schema.
		Validator validator = schema.newValidator();

		// 4. Parse the document you want to check./
		Source source = null;
		if (!xmlFileOrXmlText) {
			source = new StreamSource(new StringReader(xml));
		} else {
			source = new StreamSource(xml);
		}

		// 5. Check the document
		try {
			validator.validate(source);
			return true;
		} catch (SAXException ex) {

			System.out.println(ex.getMessage());
			return false;
		}
	}	
}
