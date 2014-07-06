package simple.foundation.freemarker.test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerTest {
	private final static Configuration cfg = new Configuration();
	
	static{		
		File f = new File("JavaSE/simple/foundation/freemarker/template");
		if(f.exists()){
			System.out.println(f.getAbsolutePath());
		}
		
		try {
			cfg.setDirectoryForTemplateLoading(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
	}
	
	/**
	 * 第一个freemarker测试
	 * @throws Exception
	 */
	@Test
	public void helloTest() throws Exception{
		// Create the root hash
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("user", "Big Joe");

		Map<String, String> latest = new HashMap<String, String>();
		root.put("latestProduct", latest);
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		
		Template temp = cfg.getTemplate("test.ftl");
		
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
		out.flush(); 		
		//System.out.println(temp.toString());
	}
	
	@Test
	public void currencyNumTest() throws Exception{
		Template temp = cfg.getTemplate("number.currency.ftl");
		
		Writer out = new OutputStreamWriter(System.out);
		temp.process(null, out);
		out.flush();
	}
	
	@Test
	public void javaDecimalNumberTest () throws Exception{
		Template temp = cfg.getTemplate("number.javaformat.ftl");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(null, out);
		out.flush();
	}
	
	@Test
	public void accountTest() throws Exception{
		// Create the root hash
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("userId", Integer.valueOf(122555));
		root.put("status", null);
		root.put("money", 12022.1223);
		root.put("unit", null);
		Template temp = cfg.getTemplate("account.xml.ftl");
		
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
		out.flush(); 		
		
	}
}
