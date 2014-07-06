package simple.foundation;


/**<pre>
 * @author slieer
 * <p>
 *  1. 所有静态的(无论其是变量还是块)都按顺序执行. 
 *
 *  2. 所有的非静态的块都在静态的(无论其是变量还是块)后执行. 
 *
 *  3. 所有的块(无论是静态的还是非静态的)都在构造之前执行. 
 *
 *  4. 静态块在类被加载到内存后就开始执行 
 *
 *  5. 非静态块是在创建对象时，构造之前被调用
 *  </p> 
 *</pre>
 *
 */
public class JavaBlock {
    public static void main(String[] args) {
        System.out.println(BlockTest.i);
    }
}


class BlockTest1{
    static{ 
        System.out.println("first static block !");
        i = 10000;
    }
    
    static int i = 100;
    
    public BlockTest1() {
        System.out.println("");
    }
    
}

class BlockTest{
    static{ 
        //System.out.println("first static block ! i=" + i);  compiler error.
        System.out.println("first static block !");
        i = 10000;
        
        //System.out.println("first static block ! i=" + i); compiler error.
    }
    
    static int i = 100;
    
    static {
        i = 1000009;
        System.out.println("second static block ! i=" + i);   
    }
    
    {
        
    }
    
    BlockTest(){
        System.out.println("default construction !");
    }
    
}