package simple.foundation.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * JUnit Suite Test
 * @author mkyong
 * 
 * The “Suite Test” means bundle a few unit test cases and run it together. 
 * In Junit, both @RunWith and @Suite annotation are used to run the suite test.
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	JunitTest1.class, 
	JunitTest2.class 
})
public class JunitTest5 {

}