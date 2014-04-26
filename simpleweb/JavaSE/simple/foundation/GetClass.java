package simple.foundation;

import java.util.Date;

/**
 * 由于getClass()在Object类中定义成了final，子类不能覆盖该方法，所以，在
 * test方法中调用getClass().getName()方法，其实就是在调用从父类继承的getClass()方法，
 * 等效于调用super.getClass().getName()方法
 * 
 * @author me
 * 
 */
@SuppressWarnings("serial")
public class GetClass extends Date {
	public static void main(String[] args) {
		new GetClass().test();
		
		float f = 3.002f;
		Object[] o = new Object[]{1, f};
		
		Object oo = o[0];
		Object oo1 = o[1];
		System.out.println();
	}

	public void test() {
		System.out.println(super.getClass().getName());
	}

	public void test1() {
		System.out.println(getClass().getSuperclass().getName());
	}
}
