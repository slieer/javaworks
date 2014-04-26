package simple.foundation.junit;

import java.util.Arrays;
import java.util.Collection;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
 
/**
 * http://www.mkyong.com/unittest/junit-4-tutorial-6-parameterized-test/
 * The “Parameterized Test” means vary parameter value for unit test. 
 * In JUnit, both @RunWith and @Parameter annotation are use to provide parameter value for unit test, 
 * @Parameters have to return List[], and the parameter will pass into class constructor as argument.
 * 
 * JUnit Parameterized Test
 * @author mkyong
 */
@RunWith(value = Parameterized.class)
public class JunitTest6 {
 
	 private int number;
 
	 public JunitTest6(int number) {
	    this.number = number;
	 }
 
	 @Parameters
	 public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { { 1 }, { 2 }, { 3 }, { 4 } };
	   return Arrays.asList(data);
	 }
 
	 @Test
	 public void pushTest() {
	   System.out.println("Parameterized Number is : " + number);
	 }
 
}