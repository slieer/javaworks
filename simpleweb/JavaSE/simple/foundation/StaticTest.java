package simple.foundation;

public class StaticTest {
    public static int MODEL = 1;
    
    static{
        MODEL = 100;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(MODEL);
    }

}
