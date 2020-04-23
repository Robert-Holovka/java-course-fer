package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	public void constructValueWrapper() {
		try {
			@SuppressWarnings("unused")
			var wrapper = new ValueWrapper(null);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void getWrapperValue() {
		var wrapper = new ValueWrapper(5);
		assertEquals(5, wrapper.getValue());
	}

	@Test
	public void setWrapperValue() {
		var wrapper = new ValueWrapper("bzvz");
		wrapper.setValue("novo");
		assertEquals("novo", wrapper.getValue());
	}

	@Test
	public void operationWithInvalidTypes() {
		var wrapper = new ValueWrapper(new Object());
		assertThrows(IllegalArgumentException.class, () -> wrapper.add(3));
	}

	@Test
	public void stringDoesNotContainNumber() {
		var wrapper = new ValueWrapper("nije broj");
		assertThrows(NumberFormatException.class, () -> wrapper.add(3));
	}

	@Test
	public void addDemo1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	public void addDemo2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertEquals(13.0, v3.getValue());
		assertEquals(1, v4.getValue());
	}

	@Test
	public void addDemo3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
	}

	@Test
	public void addDemo4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(IllegalArgumentException.class, () -> v7.add(v8.getValue()));
	}

	@Test
	public void addTwoIntegers() {
		var wrapper = new ValueWrapper(5);
		wrapper.add(3);
		assertEquals(8, wrapper.getValue());
	}

	@Test
	public void subtractNullAndInteger() {
		var wrapper = new ValueWrapper(null);
		wrapper.subtract(3);
		assertEquals(-3, wrapper.getValue());
	}

	@Test
	public void divideNullAndDouble() {
		var wrapper = new ValueWrapper(null);
		wrapper.divide(10.0);
		assertEquals(0.0, wrapper.getValue());
	}

	@Test
	public void divideTwoIntegersResultDouble() {
		var wrapper = new ValueWrapper(6);
		wrapper.divide(4);
		assertEquals(1.5, wrapper.getValue());
	}

	@Test
	public void multiplyIntegerAndDouble() {
		var wrapper = new ValueWrapper(5);
		wrapper.multiply(10.0);
		assertEquals(50.0, wrapper.getValue());
	}

	@Test
	public void addStringAndNull() {
		var wrapper = new ValueWrapper("5");
		wrapper.add(null);
		assertEquals(5, wrapper.getValue());
	}

	@Test
	public void subtractStringAndInteger() {
		var wrapper = new ValueWrapper("5");
		wrapper.subtract(2);
		assertEquals(3, wrapper.getValue());
	}

	@Test
	public void divideStringAndDouble() {
		var wrapper = new ValueWrapper("5");
		wrapper.divide(2.5);
		assertEquals(2.0, wrapper.getValue());
	}

	@Test
	public void multiplyStringAndString() {
		var wrapper = new ValueWrapper("5");
		wrapper.multiply("3");
		assertEquals(15, wrapper.getValue());
	}

	@Test
	public void numCompareWithInvalidTypes() {
		var wrapper = new ValueWrapper(new Object());
		assertThrows(IllegalArgumentException.class, () -> wrapper.numCompare(4));
	}

	@Test
	public void numCompareNullAndNull() {
		var wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare(null));
		assertEquals(null, wrapper.getValue());
	}

	@Test
	public void numCompareNullAndInteger() {
		var wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare(0));
	}

	@Test
	public void numCompareNullAndDouble() {
		var wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare(0.0));
	}

	@Test
	public void numCompareNullAndString() {
		var wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare("0.0"));
	}

	@Test
	public void numCompareIntegerAndInteger() {
		var wrapper = new ValueWrapper(3);
		assertEquals(-1, wrapper.numCompare(5));
	}

	@Test
	public void numCompareStringAndString() {
		var wrapper = new ValueWrapper("5");
		assertEquals(1, wrapper.numCompare("3"));
	}

	@Test
	public void numIntegerAndDouble() {
		var wrapper = new ValueWrapper("5");
		assertEquals(0, wrapper.numCompare("5.0"));
	}
}
