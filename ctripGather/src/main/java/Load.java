import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class Load {

	public static WebDriver getWebDriver(String url) {
		String phantomJsExe = "D:/devTools/phantomjs/phantomjs-2.1.1-windows/bin/phantomjs.exe";
		System.setProperty("phantomjs.binary.path", phantomJsExe);
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);
		return driver;
	}

	public static File getFile() throws IOException{
		String url = "http://flights.ctrip.com/international/round-beijing-bangkok-bjs-bkk?2016-12-22&2016-12-29&y_s";
		WebDriver web = getWebDriver(url);
		
		File file = new File("xiecheng.html");
		if(file.exists())file.delete();
		
		file.createNewFile();
		FileWriter w = new FileWriter(file);
		w.write(web.getPageSource());
		
		return file;
	}
	
	public static void main(String[] args) throws IOException {
//		File file = getFile();
		File file = new File("xiecheng.html");
		Document doc = Jsoup.parse(file, "utf-8");
		Elements e = doc.select("#result-wrapper .flight-item");
		e.forEach(item -> {
			Elements row = item.select(".flight-row");			
			Elements base = row.select(".flight-col-base");
//			String logoUrl = base.select(".airline-logo").attr("href");
			String airName = base.select(".airline-name").text();
			String flightNo = base.select(".flight-No flight-highlight").text();
						
			Elements detail = row.select(".flight-col-detail");
			String to = detail.select(".flight-detail-depart").text();
			String _return = detail.select(".flight-detail-return").text();
			
			String price = item.select(".seats-list .price").text();
			
			System.out.println(airName + "##" + flightNo + "##" + to  + "##" +  _return  + "##" +  price);
		});
	}
}
