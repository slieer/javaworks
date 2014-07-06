package simple.foundation.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRegexTest {
    static String[] urls = {
            "http://www.qqgb.com/Program/Java/JavaFAQ/JavaJ2SE/Program_146959.html",
            "ftp://baike.baidu.com/view/230199.htm?fr=ala0_1",
            "www.google.cn/gwt/x?u=http%3A%2F%2Fanotherbug.blog.chinajavaworld.com%2Fentry%2F4550%2F0%2F&btnGo=Go&source=wax&ie=UTF-8&oe=UTF-8",
            "http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go"
    };
    public static void main(String[] args) {
        Pattern p = Pattern.compile("^(http|www|ftp|){1}:.*");
        Matcher m = p
                .matcher(urls[0]);

        if (m.find()) {
            System.out.println(m.group());
        }
    }

    
    void test(){
        Pattern p = Pattern.compile("^(http|www|ftp|)?(://)?(//w+(-//w+)*)(//.(//w+(-//w+)*))*((://d+)?)(/(//w+(-//w+)*))*(//.?(//w)*)(//?)?(((//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*(//w*%)*(//w*//?)*(//w*:)*(//w*//+)*(//w*//.)*(//w*&)*(//w*-)*(//w*=)*)*(//w*)*)$",Pattern.CASE_INSENSITIVE );   
        Matcher m = p
                .matcher("http://www.qqgb.com/Program/Java/JavaFAQ/JavaJ2SE/Program_146959.html");

        if (m.find()) {
            System.out.println(m.group());
        }

        m = p.matcher("http://baike.baidu.com/view/230199.htm?fr=ala0_1");

        if (m.find()) {
            System.out.println(m.group());
        }

        m = p.matcher("http://www.google.cn/gwt/x?u=http%3A%2F%2Fanotherbug.blog.chinajavaworld.com%2Fentry%2F4550%2F0%2F&btnGo=Go&source=wax&ie=UTF-8&oe=UTF-8");

        if (m.find()) {
            System.out.println(m.group());
        }

        m = p.matcher("http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go");

        if (m.find()) {
            System.out.println(m.group());
        }        
    }
}
