package simple.foundation.junit;

import org.junit.Ignore;
import org.junit.Test;

/**
 * JUnit Expected Exception Test
 * 
 * @author mkyong
 */
public class JunitTest2 {

	/*
	 * In this example, the divisionWithException() method will throw an ArithmeticException Exception, 
   * since this is an expected exception, so the unit test will pass.
	 */
	@Test(expected = ArithmeticException.class)
	public void divisionWithException() {
		int i = 1 / 0;
	}

	
	/*
	 * This “Ignored” means the method is not ready to test, 
	 * the JUnit engine will just bypass this method.
	 */
	@Ignore("Not Ready to Run")  
	@Test
	public void division() {  
	  System.out.println("Method is not ready yet");
	}  
	
}
