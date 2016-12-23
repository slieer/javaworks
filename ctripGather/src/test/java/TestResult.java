


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.zhai.LoadHtml;

public class TestResult {
	@Test
	/**测试携程数据的抓取*/
	public void getCtripData() throws IOException{
		LoadHtml.parse();
	}
	
	@Test
	public void loadLocalFile() {
		String url = "http://flights.ctrip.com/";
		String html = LoadHtml.requestHtmlPage(url);
		System.out.println(html);
		Document doc = Jsoup.parse(html);
		
		Elements elements = doc.select("form input");
		elements.forEach(item ->{
			System.out.println(item.text());
		});
	}
}
