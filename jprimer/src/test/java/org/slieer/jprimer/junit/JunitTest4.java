package org.slieer.jprimer.junit;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static com.google.common.math.IntMath.divide;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit TimeOut Test
 * @author mkyong
 *
 *The “Time Test” means if an unit test takes longer than the specified number of milliseconds to run, 
 *the test will terminated and mark as failed.
 */
public class JunitTest4 {
 
	@Test
	public void infinity() {

		assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
			// 这里是要测试的耗时代码逻辑
			// 例如模拟一个耗时的操作，如等待一段时间或执行一个复杂的计算
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	void exceptionTesting() {
		Exception exception = assertThrows(ArithmeticException.class, () ->
				divide(1, 0, RoundingMode.CEILING));
		assertEquals("/ by zero", exception.getMessage());
	}

	@Test
	void timeoutNotExceeded() {
		// The following assertion succeeds.
		assertTimeout(ofMinutes(2), () -> {
			// Perform task that takes less than 2 minutes.
		});
	}

	@Test
	void timeoutNotExceededWithResult() {
		// The following assertion succeeds, and returns the supplied object.
		String actualResult = assertTimeout(ofMinutes(2), () -> {
			return "a result";
		});
		assertEquals("a result", actualResult);
	}

	@Test
	void timeoutNotExceededWithMethod() {
		// The following assertion invokes a method reference and returns an object.
		String actualGreeting = assertTimeout(ofMinutes(2), JunitTest4::greeting);
		assertEquals("Hello, World!", actualGreeting);
	}

	@Test
	void timeoutExceeded() {
		// The following assertion fails with an error message similar to:
		// execution exceeded timeout of 10 ms by 91 ms
		assertTimeout(ofMillis(10), () -> {
			// Simulate task that takes more than 10 ms.
			Thread.sleep(100);
		});
	}

	@Test
	void timeoutExceededWithPreemptiveTermination() {
		// The following assertion fails with an error message similar to:
		// execution timed out after 10 ms
		assertTimeoutPreemptively(ofMillis(10), () -> {
			// Simulate task that takes more than 10 ms.
			new CountDownLatch(1).await();
		});
	}

	private static String greeting() {
		return "Hello, World!";
	}



}
