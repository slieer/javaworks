package com.zhai;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LoadHtml {
	/**
	 *window
	 *https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-windows.zip 
	 * */
	public static String phantomJsExe = "D:/devTools/phantomjs/phantomjs-2.1.1-windows/bin/phantomjs.exe";
	/**
	 * linux
	 * 需要自己安装phantomjs
	 * */
//	phantomJsExe = "/usr/bin/phantomjs";
	public static WebDriver getDriver(){
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsExe);
		
		DesiredCapabilities desired = DesiredCapabilities.chrome();
		desired.setJavascriptEnabled(true);
		desired.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, "--logLevel=DEBUG --port=9000");  
		WebDriver driver = new PhantomJSDriver(desired);
		return driver;
	}
	
	public static String requestHtmlPage(String url) {
		WebDriver driver = getDriver();
		driver.get(url);
		return driver.getPageSource();
	}

	/**
	 * 抓取动态内容后,获取字符串，然后直接解析。
	 * 采用jsoup方式解析html, 相对来说简单一些，可以采用类似于jquery的思维来做。
	 * 这个解析过程可以自己的业务需求,写的更加详细些，以下只是示例:
	 * */
	public static void parse() throws IOException{
		String url = "http://flights.ctrip.com/international/round-beijing-bangkok-bjs-bkk?2016-12-24&2016-12-29&y_s";
		WebDriver driver = getDriver();
		driver.get(url);
		String pageSource = driver.getPageSource();
		Document doc = Jsoup.parse(pageSource);
		
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
		
	public static void main(String[] args){
	}
}
