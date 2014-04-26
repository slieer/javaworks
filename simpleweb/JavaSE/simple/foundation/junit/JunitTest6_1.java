package simple.foundation.junit;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

//(1)为准备使用参数化测试的测试类指定特殊的运行器 org.junit.runners.Parameterized。
@RunWith(Parameterized.class)
public class JunitTest6_1 {
	// (2)为测试类声明几个变量，分别用于存放期望值和测试所用数据。
	int result;
	int adddata_a;
	int adddata_b;

	// (3)为测试类声明一个带有参数的公共构造函数，并在其中为第二个环节中声明的几个变量赋值。
	public JunitTest6_1(int result, int adddata_a, int adddata_b) {
		this.adddata_a = adddata_a;
		this.adddata_b = adddata_b;
		this.result = result;
	}

	// (4)为测试类声明一个使用注解 org.junit.runners.Parameterized.Parameters 修饰的，返回值为
	// java.util.Collection 的公共静态方法，并在此方法中初始化所有需要测试的参数对。
	@Parameters
	public static Collection multipleValues() {
		return Arrays.asList(new Object[][] { { 6, 3, 3 }, { 7, 3, 4 }, });
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void before() {
	}

	@After
	public void after() {
	}

	@Test()
	public void testadd() {
		JDemo a = new JDemo();
		// assertEquals(6,a.add(3, 3));
		assertEquals(result, a.add(adddata_a, adddata_b));

		System.out.println(result + " " + adddata_a + " " + adddata_b);
		System.out.println("======");
	}
}

class JDemo {
	int a;
	int b;
	int result;

	public int add(int a, int b) {
		result = a + b;
		return result;
	}
}
