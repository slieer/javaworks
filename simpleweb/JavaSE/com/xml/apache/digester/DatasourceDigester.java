package com.xml.apache.digester;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.digester3.Digester;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DatasourceDigester {
	private final static File FILE = new File("JavaSE/com/xml/apache/digester/xml.database.xml");
	private Vector<DatasourceBean> datasources = null;

	public Vector<DatasourceBean> getDatasources() {
		return datasources;
	}

	public void setDatasources(Vector<DatasourceBean> datasources) {
		this.datasources = datasources;
	}

	public void run() throws IOException {
		Digester digester = new Digester();
		datasources = new Vector<DatasourceBean>();
		// 把当前对象压入到digester栈中。
		digester.push(this);
		/** 设定解析此xml文件的规则 */
		digester.addCallMethod("datasources/datasource", "adddatasource", 5);

		digester.addCallParam("datasources/datasource/name", 0);
		digester.addCallParam("datasources/datasource/driver", 1);
		digester.addCallParam("datasources/datasource/url", 2);
		digester.addCallParam("datasources/datasource/username", 3);
		digester.addCallParam("datasources/datasource/password", 4);
		try {
			// 确定解析目标
			digester.parse(FILE);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	public void adddatasource(String name, String driver, String url,
			String username, String password) {
		DatasourceBean data = new DatasourceBean();
		data.setName(name);
		data.setUrl(url);
		data.setUsername(username);
		data.setPassword(password);
		getDatasources().add(data);
	}

	@Test
	public void test() {
		DatasourceDigester digest = new DatasourceDigester();
		try {
			digest.run();
			
			List<DatasourceBean> list = digest.getDatasources();
			for(DatasourceBean b : list){
				System.out.println(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class DatasourceBean {
	private String name;
	private String driver;
	private String url;
	private String username;
	private String password;	
	
	@Override
	public String toString() {
		return "DatasourceBean [name=" + name + ", driver=" + driver + ", url="
				+ url + ", username=" + username + ", password=" + password
				+ "]";
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
}
