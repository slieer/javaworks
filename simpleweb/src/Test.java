import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Test {
	public static void main(String[] args) {
		//testSplit();
	    //get();
	    sudaTest();
	}

	
	public static void sudaTest(){
	    String key="a32b7b3c1b73c9296e651b5008c97209";
	    String cid="71";
        String startDate = "2012-09-17";
        String endDate = "2012-09-18";
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date provDate = cal.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDate = endDate = df.format(provDate);
        
        String tempString = startDate.concat(endDate).concat(cid).concat(key);
        String token = genMd5(tempString);
        
        String url = "http://www.mobeehome.com/payment/port/query_order.action?startdate="
            .concat(startDate)
            .concat("&enddate=").concat(endDate)
            .concat("&token=").concat(token)
            .concat("&");
        System.out.println(url);
	}
	
    private static String genMd5(String tempStr) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset(); 
            messageDigest.update(tempStr.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest(); 
            String newStr = bytesToHexString(byteArray);
            
            return newStr.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
	
	
	
	public static void get(){
	    String s  = "2.00|275025|af390bdee62352b02286134da9379317|20120815|20121725169837|50.0|RMB|GWC12081513153276972||CMJFK00010001||P|1";
	    String[] st = s.split("\\|");
	    System.out.println(st[st.length -1] + "-" + st[st.length -2]);
	    
	    List<String> list = Arrays.asList(st);
	    System.out.println(list.get(list.size()-1));
	    System.out.println(list.get(list.size()-2));
	}
	
    private static void testSplit() {
        try {
			String string  = URLEncoder.encode(",","UTF-8");
			System.out.println(string);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
