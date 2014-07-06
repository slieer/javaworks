package com.xml.stax;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxSimpleDemo {
	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		
		try {
			saxParser = spf.newSAXParser();
			saxParser.parse(new File("JavaSE\\com\\xml\\book.xml"), new DefaultHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class SAXHandler extends DefaultHandler{

	@Override
	public void startDocument() throws SAXException {
		System.out.println("start ......");
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("end ......");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.println("SAX Event: start Element[" + qName + "]");
		for (int i = 0; i < attributes.getLength(); i++) {
			System.out.println("ATTRIBUTE: " + attributes.getLocalName(i) + " VALUE:" + attributes.getValue(i));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("SAX Event: END ELEMENT[" + qName + "]");
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("SAX Event: CHARACTERS[] 开始");
		OutputStreamWriter outw = new OutputStreamWriter(System.out);
		try {
			outw.write(ch, start, length);
			outw.flush();
			outw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("SAX Event: CHARACTERS[] 结束");
	}
}