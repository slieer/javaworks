package com.xml;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * http://www.cnblogs.com/tclee/archive/2011/04/11/2012773.html
 * @author slieer
 * Create Date2011-10-12
 * version 1.0
 */
public class BeanXML {
	/**
	 * * DMO4J写入XML
	 * 
	 * @param obj
	 *            泛型对象
	 * @param entityPropertys
	 *            泛型对象的List集合
	 * @param Encode
	 *            XML自定义编码类型(推荐使用GBK)
	 * @param XMLPathAndName
	 *            XML文件的路径及文件名
	 */
	public <T> StringWriter writeXmlDocument(T obj, List<T> entityPropertys,
			String Encode) {
		long lasting = System.currentTimeMillis();// 效率检测
		try {
			XMLWriter writer = null;// 声明写XML的对象

			// 新建student.xml文件并新增内容
			Document document = DocumentHelper.createDocument();
			String rootname = obj.getClass().getSimpleName();// 获得类名
			Element root = document.addElement(rootname + "s");// 添加根节点
			Field[] properties = obj.getClass().getDeclaredFields();// 获得实体类的所有属性
			for (T t : entityPropertys) {
				// 递归实体
				Element secondRoot = root.addElement(rootname); // 二级节点
				for (int i = 0; i < properties.length; i++) { // 反射get方法

					Method meth = t.getClass().getMethod(
							"get"
									+ properties[i].getName().substring(0, 1)
											.toUpperCase()
									+ properties[i].getName().substring(1)); // 为二级节点添加属性，属性值为对应属性的值
					secondRoot.addElement(properties[i].getName()).setText(
							meth.invoke(t).toString());
				}
			} 
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Encode);// 设置XML文件的编码格式

			// 生成XML文件
			StringWriter stringWriter = new StringWriter();
			writer = new XMLWriter(stringWriter, format);
			writer.write(document);
			writer.close();
			
			
			long lasting2 = System.currentTimeMillis();
			System.out.println("写入XML文件结束,用时" + (lasting2 - lasting) + "ms");
			
			return stringWriter;
		} catch (Exception e) {
			System.out.println("XML文件写入失败");
		}
		
		return null;
	}
}
