package simple.foundation;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Test;


public class FormatTest {
	public static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy年MM月dd日");
	
	public static void main(String[] args) {
		new FormatTest().dateFormat();
	}
	
	@Test
	public void dateFormat1(){
		String DATE_FORMAT = "yyMMddHHmmss";
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String d = dateFormat.format(new Date());
		System.out.println(d);
		
	}	
	
	@Test
	public void dateFormat(){
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String d = dateFormat.format(new Date());
		System.out.println(d);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 10);
		d = dateFormat.format(cal.getTime());
		System.out.println(d);

		MessageFormat SIMPLE_NODE_FORMAT = new MessageFormat("<{0}>{1}</{0}>");
		String NODE_LASTMODIFY = "last";
		
		d = SIMPLE_NODE_FORMAT.format(new Object[]{NODE_LASTMODIFY,dateFormat.format(cal.getTime())});
		System.out.println(d);
	}
		
	@Test
	public void numFormat() {
		System.out.println(dateFormat.format(new Date()));

		DecimalFormat format = new DecimalFormat("###.##");
		System.out.println(format.format(101010.000022));

		DecimalFormat format1 = new DecimalFormat(".00");
		System.out.println(format1.format(101010.000022));
		
		
		//字符串填充
		String result = MessageFormat.format(
				"At {0}, there was {1} on planet {2}.",
				"one", "two", "three");

		System.out.println(result);
	}
	
	/**
	 * 请最前面的{ ，不被解析。
	 */
	@Test
	public void format(){
        String resultFormat = "'{'\"md5\":\"{0}\",\"len\":\"{1,number,#}\"'}'";
        String result = MessageFormat.format(resultFormat, "AAAFS22D", 121312312);
        System.out.println(result);
        JSONObject ob = JSONObject.fromObject(result);
        System.out.println(ob.getLong("len"));
	}
}
