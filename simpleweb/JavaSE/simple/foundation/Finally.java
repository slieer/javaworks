package simple.foundation;

import org.junit.Test;

public class Finally {
	Finally t = new Finally();
	
	@Test
	public void test(){
		System.out.println(t.get1());
	}
	
	@Test
	public void test1(){
		int b = t.get();
		System.out.println(b);    // 2		
	}
	
	int get1() {
		int x = 1;
		try {
			return x;
		} finally {
			++x;
		}
	}

	//finally block does not complete normally
	int get() {
		try {
			return 1;
		} finally {
			return 2;
		}
	}
	
}
