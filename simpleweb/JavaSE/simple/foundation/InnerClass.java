package simple.foundation;

import java.util.ArrayList;
import java.util.List;

public class InnerClass<T> {
	public static void main(String[] args) {
		get(new ArrayList<String>());
	}

	void f() {
		new A();
	}

	class A {
		int i = 0;

		// static int ii = 1; //error.
	}

	static class B {
		static int i = 1;
	}

	A x = new A() {

	};

	Comparable<T> comparable = new Comparable<T>() {

		@Override
		public int compareTo(T o) {
			return 0;
		}

	};

	static class comparable<T> implements Comparable<T> {

		@Override
		public int compareTo(T o) {
			return 0;
		}

	};

	static <T extends List> List<T> get(T t) {
		System.out.println("Hi " + t);
		return null;
	}

	static <T extends List<? extends Object>> T get1() {
		return null;
	}
}
