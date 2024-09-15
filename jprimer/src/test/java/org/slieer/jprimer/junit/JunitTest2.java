package org.slieer.jprimer.junit;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * JUnit Expected Exception Test
 * 
 * @author mkyong
 */
@DisplayName("A special test case")
public class JunitTest2 {

	/*
	 * In this example, the divisionWithException() method will throw an ArithmeticException Exception, 
   * since this is an expected exception, so the unit test will pass.
	 */
	@Test
	@DisplayName("Custom test name containing spaces")
	public void divisionWithException() {
		int i = 1 / 0;
	}

	
	/*
	 * This “Ignored” means the method is not ready to test, 
	 * the JUnit engine will just bypass this method.
	 */
	@Disabled("Not Ready to Run")
	@Test
	public void division() {  
	  System.out.println("Method is not ready yet");
	}


	@RepeatedTest(3)
	void foo() {


	}
	
}
