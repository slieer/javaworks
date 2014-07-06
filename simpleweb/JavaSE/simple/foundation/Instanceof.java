package simple.foundation;

public class Instanceof {
	/*
	 * 使用 “instance of”时，你需要在代码编译的时候明确知道 “B” 的class；
	 * 使用"isAssignableFrom"，你可以在runtime时动态地传入B的class。
	 */
	private static boolean aIsInstanceOfB(Object a, Class<?> b) {
		return b.isAssignableFrom(a.getClass());
	}

	public static void main(String[] args) {
		String a = "A";
		System.out.println(aIsInstanceOfB(a, String.class));
		
		if(a instanceof String){
			System.out.println("string.");
		}
	}
}
