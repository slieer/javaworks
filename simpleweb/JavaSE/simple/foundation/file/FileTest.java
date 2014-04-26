package simple.foundation.file;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileTest {
	private static Logger log = Logger.getLogger(FileTest.class);  //可以静态方法中用。
	private Logger lo = Logger.getLogger(this.getClass());  //不能在静态方法中用。
	
	@Test
	public void getFilePath(){
		String path = getFilePath("");
		log.info(path);
	}
	
	public void getFile(){
		URL u = this.getClass().getResource("");
		log.info(u.getPath());
	}
	
	/**
	 *同 URL u = this.getClass().getResource("");
	 * @param resourceName
	 * @return
	 */
	public String getFilePath(String resourceName) {
		Package pack = this.getClass().getPackage();
	    String packageName = pack.getName();
	    String classname_resource = "/" + packageName.replace('.', '/') + "/" + resourceName;
	    URL url = this.getClass().getResource(classname_resource);
	    String urlPath = url.getPath();
	    File classFile = new File(urlPath);
	    return classFile.getPath();
	}	

}
