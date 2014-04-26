package simple.foundation;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BoolTest {
    @Test
    public void testBool(){
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        
        m.put("a", Boolean.TRUE);
        m.put("a1", Boolean.TRUE);
        m.put("a2", Boolean.FALSE);
        
        if(m.get("a") == true){
            System.out.println("hi 0!");
        }

        
        //if(m.get("x")){   error
        //if(m.get("x") == true){   error
        if(m.get("x") == Boolean.TRUE){
            System.out.println("hi!");
        }
        
        if(m.get("x") == Boolean.FALSE){
            System.out.println("hi xxx!");
        }
        
        if(m.get("x") == null){
            System.out.println("hi null !");
        }


    }
}
