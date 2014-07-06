package simple.foundation.junit;

import org.junit.Test;

/**
 * JUnit TimeOut Test
 * @author mkyong
 *
 *The “Time Test” means if an unit test takes longer than the specified number of milliseconds to run, 
 *the test will terminated and mark as failed.
 */
public class JunitTest4 {
 
	@Test(timeout = 1000)  
	public void infinity() {  
		while (true);  
	}  
 
}
