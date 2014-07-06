package simple.foundation;

/** *//**
 * 
 * <p>
 * Description: 打印整型的二进制格式
 * </p>
 * <p>
 * Copyright 2006 mjn.
 * </p>
 * 
 * @author ma jia nan
 * @Create Date : 2008-02-24
 * @Version : 1.0
 */
public class PrintBinary {
    /** *//**
     * 打印整型的二进制格式
     * 
     * @param x
     * @return
     */
    public void printBinaryNumber(int x) {
    	
        int[] buffer = new int[32];
        for (int i = 0, mask = 1; i < 32; i++) {
            buffer[i] = (mask << i & x) >> i;
        }
    }
    
    public String f(int x){
    	return Integer.toBinaryString(x);
    }
}
