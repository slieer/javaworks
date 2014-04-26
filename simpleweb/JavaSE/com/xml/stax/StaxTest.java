package com.xml.stax;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.junit.Test;
import org.w3c.dom.Attr;

import com.sun.xml.internal.stream.events.AttributeImpl;

public class StaxTest {
	@Test
	public void staxTest() throws Exception {
		URL url = this.getClass().getResource("/");
		String xmlFile = url.getPath() + "com/xml/unformattedXML.xml";
		File file = new File(xmlFile);

		// Reader stream = new FileReader(file);  //error
		InputStream stream = new FileInputStream(file);
		XMLEventReader xsr = XMLInputFactory.newInstance()
				.createXMLEventReader(stream);

		Deque<String> deque = new LinkedList<String>();
		while (xsr.hasNext()) {
			XMLEvent evt = xsr.nextEvent();
			switch (evt.getEventType()) {
			case XMLEvent.START_ELEMENT: {
				StartElement s = evt.asStartElement();
				Iterator<Namespace> NSs = s.getNamespaces();
				
				StringBuilder elHeader = new StringBuilder();
				while(NSs.hasNext()){
					Namespace NS = NSs.next();
					QName attr = NS.getName();
					String name = attr.getPrefix();					
					String val = NS.getValue();
					elHeader.append(name);
					elHeader.append("=\"");
					elHeader.append(val.concat("\" "));
				}
				Iterator<AttributeImpl> attrs = s.getAttributes();
				while (attrs.hasNext()) {
					AttributeImpl attr = attrs.next();
					String attrName = attr.getName().getLocalPart();
					String attrVal = attr.getValue();
					elHeader.append(attrName);
					elHeader.append("=\"");
					elHeader.append(attrVal.concat("\""));
				}
				//System.out.println(elHeader.toString());
				
				QName name = s.getName();
				String tagName = name.getLocalPart();
				String top = deque.peekFirst();
				
				if(top != null){
					System.out.println();					
				}
				
				if(top == null || !top.equals(tagName)){
				}
				deque.push(tagName);
				
				//System.out.println("---" + deque.size() + "---");
				String head = null;
				if(elHeader.length() == 0){
					head = "<".concat(tagName).concat(">");					
				}else{
					head = "<".concat(tagName).concat(" ").concat(elHeader.toString()).concat(">");
				}
				System.out.print(head);
				//System.out.print(s.toString());
				break;
			}
			case XMLEvent.END_ELEMENT :{
				EndElement e = evt.asEndElement();
				String name = e.getName().getLocalPart();
				System.out.print("</".concat(name).concat(">"));
				
				//System.out.println("-----name:" + name + ", top:" + deque.peekFirst());
				//deque.removeFirst();
				deque.push(name);
				break;
			}
			case XMLEvent.CHARACTERS :{
				Characters se = evt.asCharacters();
				
				System.out.print(se.toString());
				break;
			}
			}
		}
		
		System.out.println(deque);
	}
	
	/**
	 * http://java.sun.com/webservices/reference/tutorials/jaxp/html/stax.html
	 * @throws Exception
	 */
	public void staxStream() throws Exception{
		XMLOutputFactory output = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = output.createXMLStreamWriter(System.out);
		
	}
	
}
