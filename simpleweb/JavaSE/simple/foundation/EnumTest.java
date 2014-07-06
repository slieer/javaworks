package simple.foundation;

public class EnumTest {
	enum Field{
		AUID,MSISDN,TOKEN,QUERYSTRING
	}

	public static void main(String[] args) {
		//testColor();
		
		System.out.println(Field.AUID.toString());
		System.out.println(Field.AUID.name());
		
	}

	private static void testColor() {
		Color color = Color.RED;

		switch (color) {
		case RED:
			System.out.println("it's red");
			break;

		case BLUE:
			System.out.println("it's blue");
			break;

		case BLACK:
			System.out.println("it's blue");
			break;
		}
	}
}

enum Color {
	// 枚举值都是public static final
	RED(255, 0, 0), BLUE(0, 0, 255), BLACK(0, 0, 0), YELLOW(255, 255, 0), GREEN(
			0, 255, 0);

	// 构造枚举值，比如RED(255,0,0)
	private Color(int rv, int gv, int bv) {
		this.redValue = rv;
		this.greenValue = gv;
		this.blueValue = bv;
	}

	public String toString() { // 覆盖了父类Enum的toString()
		return super.toString() + "(" + redValue + "," + greenValue + ","
				+ blueValue + ")";
	}

	private int redValue; // 自定义数据域，private为了封装。
	private int greenValue;
	private int blueValue;

}
