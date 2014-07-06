package simple.foundation.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.From;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class ValidateEmail {
    @Test
    public void testEmail(){
        String[] email = new String[5];
        email[0] = "abc@g.cn";
        email[1] = "sli@z.cn";
        email[2] = "abc@g.cn";
        email[3] = "slieer@t.cn";
        email[4] = "";
        System.out.println(validateEmail(email));
    }
    
    @Test
    public void test(){
        String[] arr = new String[]{
        "MACBOOKPRO-F4E0????",
        "ZHANGLIANGFU-PC??D??",
        "JOHNNY-B1ED9245??D??",
        "???7Y????????????????????????", 
        "BORQS-0F49646EB??D??",
        "BOSITONG-A07EF4??D??",
        "SZBORQS-FENGHUA??D??",
        "SKYWORTH-294CD3??D??",
        "SKYWORTH-294CD3??D??",
        "DELL-D336F429E4??D??",
        "SKWORTH-294CD33??D??"
        };
        
        for(String name : arr){
            if(name.matches("^[a-zA-Z0-9].*")){
                System.out.println(name + ",OK");
                System.out.println(name.replaceAll("[^a-zA-Z0-9-].*", ""));
            }
        }
    }
    
    public static boolean validateEmail(String[] email){
        if(email == null || email.length == 0){
            return false;
        }
        final String EMAIL_EXGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(EMAIL_EXGEX);
        for(int i = 0; i < email.length; i++){
            if(!email[i].equals("")){
                Matcher matcher = regex.matcher(email[i]);          
                boolean match = matcher.matches();
                if(! match){
                    return false;
                }               
            }
        }
        return true;
    }
    
    @Test
    public void testReser(){
        String s = "/mnt/smb/mountpoint_01|smb://slieer:slieer@192.168.51.46/music/";
        String [] ar = s.split("[|]");
        System.out.println(ar[0]);
        System.out.println(ar[1]);
        
        String [] ar1 = s.split("\\|");
        System.out.println(ar1[0]);
        System.out.println(ar1[1]);
        
    }
    
    @Test
    public void testFen(){
        String s = "/sdcard/Alarms/test";
        String[] arr = s.split("/");
        for(String ss : arr){
            System.out.println(ss);
        }
        
        
        String ss = "/mnt/smb/192.168.51.66/music";
        ss.split("/");
        System.out.println("/mnt/" + ss.replace("/mnt/smb/", "").replaceAll("/", "-"));
    }
}
