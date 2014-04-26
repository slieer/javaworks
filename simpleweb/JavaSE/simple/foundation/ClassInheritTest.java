package simple.foundation;

public class ClassInheritTest {
	public static void main(String[] args) {
		ABC abc = new BC();
		abc.f1();
		System.out.println(abc.a);
		//System.out.println(abc.b);
		System.out.println(abc.obj);
	}
}

class ABC {
	static int b = 6;
	int a = 5;
	//ABC obj = new ABC();
	String obj = new String("abc");

	public void f1() {
		System.out.println("1");
	}
}

class BC extends ABC {
	static int b = 1;	
	int a = 6;
	BC obj = new BC();
	public void f1() {
		// System.out.println(super.a);//5
		// System.out.println(this.a);
		System.out.println("2");
	}

	public void f2() {
		System.out.println("3");
	}
}
