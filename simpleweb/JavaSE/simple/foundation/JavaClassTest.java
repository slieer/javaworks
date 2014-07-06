package simple.foundation;

import java.math.BigInteger;

import org.apache.commons.collections.map.StaticBucketMap;

public class JavaClassTest {
	public static void main(String... args) {
		//int one = 1;
		//int a = 3;

		//System.out.println(one << a);

		// 二进制串
		//System.out.println(Integer.toBinaryString(-1)); //11111111111111111111111111111111
		// 十六进制串
		//System.out.println(Integer.toHexString(20));
		// 八进制串
		//System.out.println(Integer.toOctalString(20));

		System.out.println(f() && ff());
		System.out.println(f() & ff());    //
		System.out.println(f() | ff());    //
		System.out.println(1 & -1);
		
		byte by = 1;
	
		switch (by) {
			case 1:
				switch('c'){
				}
				break;
		}
		
		final short sh = 1;
		switch (sh) {
		case 1:
			break;
		}
		
		BigInteger bigInteger = new BigInteger("2");
	}

	static abstract class A{
		static void f(){
			
		}
	}
	static boolean f(){
		System.out.println("f");
		return false;
	}
	
	static boolean ff(){
		System.out.println("ff");
		return true;
	}
}

abstract class A{
	static void f(){		
	}
	
	final void ff(int x){
		
	}
	
	//The abstract method fff121 in type A can only set a visibility modifier, 
	//one of public or protected
	//
	//abstract static int ffffff();  error!
	//abstract native int fff12();
	//abstract synchronized int fff121();
	native synchronized int fff();
	native synchronized static int ffff();	
	final native synchronized static int fffff();
	static native synchronized int fff1();
	
	Object obj = new Object();
	
	static{
		abstract class s{
			
		}
		final class sa{
			
		}
	}
}
class a11{
	class f{
		
	}
	
	interface ss{
		
	}
}

//The member interface ff can only be defined inside a top-level class or interface
interface ss{
	
}

