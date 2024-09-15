package org.slieer.jprimer.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * http://www.mkyong.com/unittest/junit-4-tutorial-6-parameterized-test/
 * The “Parameterized Test” means vary parameter value for unit test. 
 * In JUnit, both @RunWith and @Parameter annotation are use to provide parameter value for unit test, 
 * @Parameters have to return List[], and the parameter will pass into class constructor as argument.
 * 
 * JUnit Parameterized Test
 * @author mkyong
 */
public class JunitTest6 {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void testWithValueSource(int argument) {
		assertTrue(argument > 0 && argument < 4);
	}

 

 
	 @Test
	 public void pushTest() {
	   System.out.println("Parameterized Number is : ");
	 }
 
}