package slieer.commons.Digester.person;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.binder.RulesModule;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.junit.jupiter.api.Test;

public class PersonTest {
	public static class MyRulesLoader extends FromXmlRulesModule {
		private File xmlrule;
		
		public MyRulesLoader(File xmlrule) {
			this.xmlrule = xmlrule;
		}
		
		public MyRulesLoader(URL url) {
			this.xmlrule = new File(url.getPath());
		}
		
		@Override
		protected void loadRules() {
			loadXMLRules(xmlrule);
		}
	}	
	
	@Test
	public void test() throws Exception {
		// 从XML规则集中配置Digester
		URL rules = getClass().getResource("person-rules.xml");
				
		DigesterLoader loader = DigesterLoader.newLoader(new MyRulesLoader(rules));
		Digester digester = loader.newDigester();
		//Digester digester = DigesterLoader.createDigester(rules.toURI());  
		//Digester digester = DigesterLoader.createDigester(rules);// 将空的List推入到Digester的堆栈List
		
		List<Person> people = new ArrayList<Person>();
		digester.push(people);//
		// 解析XML文档
		// InputStream input = new FileInputStream( );
		InputStream input = getClass().getResourceAsStream("person.xml");
		digester.parse(input);

		System.out.println(people);
	}

	@Test
	public void test1() throws Exception {
		//digester.setClassLoader(Thread.currentThread().getContextClassLoader());  
		
		Digester digester = new Digester();
		List<Person> people = new ArrayList<Person>();
		digester.push(people);//		
		digester.setValidating(false);
		
		digester.addObjectCreate("people/person", Person.class);

		digester.addBeanPropertySetter("people/person/name");
		digester.addBeanPropertySetter("people/person/age");
		digester.addSetNext("people/person", "add", Person.class.getName());
		
		
		InputStream input = getClass().getResourceAsStream("person.xml");
		Object obj = digester.parse(input);
		System.out.println("people:" + people);
		//System.out.println("people:" + obj);
		
	}
}
