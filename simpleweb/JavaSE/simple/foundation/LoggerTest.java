package simple.foundation;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LoggerTest {
	final static Logger log = Logger.getLogger(LoggerTest.class);
	
	public static void main(String[] args) {
		log.info("--");
	}
	
	@Test
	public void log(){
		log.info("--");
	}
	
	@Test
	public void randTest(){
		System.out.println(Math.random());
		System.out.println(Math.random());
		System.out.println(Math.random());
		System.out.println(Math.random());
	}
}
