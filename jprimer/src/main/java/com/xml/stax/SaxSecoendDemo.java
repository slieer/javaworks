package com.xml.stax;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SaxSecoendDemo extends DefaultHandler {
	private String ElementName;
	private int id;
	private String bookName;
	private String bookAuthor;
	private String bookISBN;
	private String bookPrice;

	public SaxSecoendDemo() {
		this.ElementName = "";
		this.id = 0;
		this.bookName = "";
		this.bookAuthor = "";
		this.bookISBN = "";
		this.bookPrice = "";
	}

	public void startDocument() {
		System.out.println("开始读XML文档");
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		this.ElementName = qName;
		if (qName.equals("Book")) // 识别是否是指定的标签
		{
			System.out.println("id属性：" + attributes.getValue(0)); // 打印该名称的标签的属性的值
		}
	}

	public void characters(char[] ch, int start, int length) {
		String tagBodyText = new String(ch, start, length); // 获得标签体的文字串内容
		/*
		 * 下列的代码主要是识别是否是指定的名称的标签,如果是并且识别其标签体是否为空,最后获得标签体的文字串
		 */
		if(this.ElementName.equals("id")){
			this.id = Integer.valueOf(tagBodyText);
		}
		
		if (this.ElementName.equals("bookName")
				&& !tagBodyText.trim().equals("")) {
			this.bookName = tagBodyText;
		}
		if (this.ElementName.equals("bookAuthor")
				&& !tagBodyText.trim().equals("")) {
			this.bookAuthor = tagBodyText;
		}
		if (this.ElementName.equals("bookISBN")
				&& !tagBodyText.trim().equals("")) {
			this.bookISBN = tagBodyText;
		}
		if (this.ElementName.equals("bookPrice")
				&& !tagBodyText.trim().equals("")) {
			this.bookPrice = tagBodyText;
		}
	}

	public void endElement(String uri, String localName, String qName) {
		/*
		 * 下列的代码主要是打印出对应的标签体的文字串
		 */
		if(qName.equals("id")){
			System.out.print(this.id);
		}
		
		if (qName.equals("bookName")) {
			System.out.println("bookName：" + this.bookName);
		}
		if (qName.equals("bookAuthor")) {
			System.out.println("bookAuthor：" + this.bookAuthor);
		}
		if (qName.equals("bookISBN")) {
			System.out.println("bookISBN：" + this.bookISBN);
		}
		if (qName.equals("bookPrice")) {
			System.out.println("bookPrice：" + this.bookPrice);
		}
		if (qName.equals("Book")) {
			System.out.println();
		}
		this.ElementName = "";
	}

	public void endDocument() {
		System.out.println("XML文档结束");
	}

	public static void main(String[] args) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(new File("JavaSE\\com\\xml\\booklist.xml"), new SaxSecoendDemo());
	}
}
