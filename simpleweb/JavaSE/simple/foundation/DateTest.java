package simple.foundation;

import java.util.Calendar;
import java.util.Date;

public class DateTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.MONTH, -1);
        int month = cal.get(Calendar.MONTH ) +1; //month [1, 12]
        int year = cal.get(Calendar.YEAR);
        
        String yearMonth = String.valueOf(year) + "-" + (month <= 9 ? "0" +month : month );
        Date lastMonth = cal.getTime();

        System.out.println(yearMonth + ",," + lastMonth.toLocaleString());
        
        
        Date date = new Date();
        System.out.println(date.getTime());
    }

}
