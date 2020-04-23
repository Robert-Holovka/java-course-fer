package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static hr.fer.zemris.java.hw01.Rectangle.*;

public class RectangleTest {

	@Test
	public void parsePositiveDoublePositiveNumber() {
		double d = parsePositiveDouble("3.14");
		assertEquals(3.14, d);
	}
	
	@Test
	public void parsePositiveDoubleNegativeNumber() {
		try {
			parsePositiveDouble("-3.14");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Unijeli ste negativnu vrijednost.", e.getMessage());
		}
	}
	
	@Test
	public void parsePositiveDoubleNotNumber() {
		try {
			parsePositiveDouble("aaa");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("'aaa' se ne može protumačiti kao broj.", e.getMessage());
		}
	}
	
	@Test
	public void calculateAreaWithValidArguments() {
		assertEquals(16.0, calculateArea(2, 8));
	}

	@Test
	public void calculatePerimeterWithValidArguments() {
		assertEquals(20.0, calculatePerimeter(2, 8));
	}
	
}
