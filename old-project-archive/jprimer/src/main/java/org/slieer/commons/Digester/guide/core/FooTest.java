package org.slieer.commons.Digester.guide.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester3.Digester;
import org.junit.Test;
import org.xml.sax.SAXException;

public class FooTest {
	public static void main(String[] args) {
		try {
			new FooTest().
			test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() throws IOException, SAXException {
		final String packageName = "org.slieer.commons.Digester.guide.core"; 
		
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("foo", packageName + ".Foo");
		digester.addSetProperties("foo");
		digester.addObjectCreate("foo/bar", packageName + ".Bar");
		digester.addSetProperties("foo/bar");
		digester.addSetNext("foo/bar", "addBar", packageName + ".Bar");

//		File data = new File("data.xml");
//		System.out.println(data.getAbsolutePath());
		InputStream in = this.getClass().getResourceAsStream("data.xml");
		Foo foo = digester.parse(in);

		System.out.println(foo);
	}
}
