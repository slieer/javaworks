package simple.foundation;

import org.junit.Test;

public class CharTest {
    
    char f(char x){
        char[] a = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        //int a = 'a';  //97
        //int b = 'A'; //65
        if(x >= 'A' && x <= 'Z'){
            return a[(x - 'A' + 5) % 26];
        }else{
            return 0;
        }
    }
    
    char f1(char x){
        return (char)('a' + (x - 'A' + 5) % 26);
    }
    public static void main(String[] args) {}
    
    @Test
    public void test(){
        
    }
}
