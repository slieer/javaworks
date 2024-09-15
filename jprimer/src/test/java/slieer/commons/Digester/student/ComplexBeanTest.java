package slieer.commons.Digester.student;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.junit.jupiter.api.Test;
import slieer.commons.Digester.student.bean.Academy;
import slieer.commons.Digester.student.bean.Course;
import slieer.commons.Digester.student.bean.Teacher;
import org.xml.sax.SAXException;

public class ComplexBeanTest {
	@Test
	public void digestTest() {
		Digester digester = new Digester();
		// 注意，此处并没有象上例一样使用 push ，是因为此处从根元素创建 了一个对 象实例
		digester.addObjectCreate("academy", Academy.class);

		// 将 < academy > 的属性与对象的属性关联
		digester.addSetProperties("academy");
		digester.addObjectCreate("academy/student", Student.class);
		digester.addSetProperties("academy/student");
		digester.addObjectCreate("academy/student/course", Course.class);
		digester.addBeanPropertySetter("academy/student/course/id");
		digester.addBeanPropertySetter("academy/student/course/name");

		digester.addSetNext("academy/student/course", "addCourse");
		digester.addSetNext("academy/student", "addStudent");

		digester.addObjectCreate("academy/teacher", Teacher.class);
		digester.addSetProperties("academy/teacher");

		// 当遇到 academy/teacher/certification 时，调用 addCertification
		digester.addCallMethod("academy/teacher/certification",
				"addCertification", 1);
		// 设置 addCertification 的参数值，此处的 0 表示这个元素体的第一个值
		// 为参数值传入 addCertification 。在此处，即为 <certification> 的值。
		// （因为就只有一个）
		digester.addCallParam("academy/teacher/certification", 0);
		digester.addSetNext("academy/teacher", "addTeacher");

		try {
			InputStream in = this.getClass().getResourceAsStream(
					"complex-bean.xml");
			Academy a = (Academy) digester.parse(in);

			System.out.print(a);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

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
		// digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		URL url = this.getClass().getResource("complex-bean-parse.xml");
		System.out.println(url);
		
		DigesterLoader loader = DigesterLoader.newLoader(new MyRulesLoader(url));
		
		//url = getClass().getClassLoader().getResource("complex-bean.xml");
		System.out.println(url);
		InputStream input = getClass().getResourceAsStream("complex-bean.xml");
		Digester digester = loader.newDigester();
		Academy a = (Academy) digester.parse(input);

		System.out.println(a);
	}
}
