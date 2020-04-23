package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static hr.fer.zemris.java.hw01.Factorial.*;

public class FactorialTest {

	@Test
	public void factorialOfNegativeOne() {
		try {
			calculateFactorial(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void factorialOfTwentyOne() {
		try {
			calculateFactorial(21);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void factorialOfZero() {
		Long factorial = calculateFactorial(0);
		assertEquals(1, factorial);
	}
	
	@Test
	public void factorialOfThree() {
		Long factorial = calculateFactorial(3);
		assertEquals(6, factorial);
	}

	@Test
	public void factorialOfFour() {
		Long factorial = calculateFactorial(4);
		assertEquals(24, factorial);
	}

	@Test
	public void factorialOfTwelve() {
		Long factorial = calculateFactorial(12);
		assertEquals(479001600, factorial);
	}
}