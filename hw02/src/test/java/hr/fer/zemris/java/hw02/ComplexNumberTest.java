package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void createComplexNumber() {
		try {
			ComplexNumber c = new ComplexNumber(30, -5);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void complexNumberToString() {
		ComplexNumber c = new ComplexNumber(30, -5);
		assertEquals("30.0 -5.0i", c.toString());
	}

	@Test
	public void equalComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(30, -5);
		ComplexNumber c2 = new ComplexNumber(30.0000007, -5.0000007);
		assertEquals(c1, c2);
	}

	@Test
	public void notEqualComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(30, -5);
		ComplexNumber c2 = new ComplexNumber(30.000002, -5.000002);
		assertFalse(c1.equals(c2));
	}

	@Test
	public void complexNumberFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(5);
		assertEquals(new ComplexNumber(5, 0), c);
	}

	@Test
	public void complexNumberFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(5);
		assertEquals(new ComplexNumber(0, 5), c);
	}

	@Test
	public void fromMagnitudeAndAngleZeros() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(0, 0);
		assertEquals(new ComplexNumber(0, 0), c);
	}

	@Test
	public void fromMagnitudeAndAngleFirstQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 1);
		assertEquals(new ComplexNumber(2.701512, 4.207355), c);
	}

	@Test
	public void fromMagnitudeAndAngleSecondQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 3);
		assertEquals(new ComplexNumber(-4.949962, 0.705600), c);
	}

	@Test
	public void fromMagnitudeAndAngleThirdQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 4);
		assertEquals(new ComplexNumber(-3.268218, -3.784012), c);
	}

	@Test
	public void fromMagnitudeAndAngleFourthQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 6);
		assertEquals(new ComplexNumber(4.800851, -1.397077), c);
	}
	
	@Test
	public void complexNumberGetReal() {
		ComplexNumber c = new ComplexNumber(5, 6);
		assertEquals(5.0, c.getReal());
	}

	@Test
	public void complexNumberGetImaginary() {
		ComplexNumber c = new ComplexNumber(5, 6);
		assertEquals(6.0, c.getImaginary());
	}

	@Test
	public void complexNumberGetMagnitude() {
		ComplexNumber c = new ComplexNumber(3, -2);
		assertTrue((3.605551 - c.getMagnitude()) < ComplexNumber.MAX_DIFFERENCE);
	}
	
	@Test
	public void getAngleFirstQuadrant() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertTrue((0.785398 - c.getAngle()) < ComplexNumber.MAX_DIFFERENCE);
	}
	
	@Test
	public void getAngleSecondQuadrant() {
		ComplexNumber c = new ComplexNumber(-1, 1);
		assertTrue((2.356194 - c.getAngle()) < ComplexNumber.MAX_DIFFERENCE);
	}
	
	@Test
	public void getAngleThirdQuadrant() {
		ComplexNumber c = new ComplexNumber(-1, -1);
		assertTrue((3.926991 - c.getAngle()) < ComplexNumber.MAX_DIFFERENCE);
	}
	
	@Test
	public void getAngleFourthQuadrant() {
		ComplexNumber c = new ComplexNumber(1, -1);
		assertTrue((5.497787 - c.getAngle()) < ComplexNumber.MAX_DIFFERENCE);
	}
		
	@Test
	public void addComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, -1);
		ComplexNumber c2 = new ComplexNumber(-1, 1);
		assertEquals(new ComplexNumber(0, 0), c1.add(c2));
	}
	
	@Test
	public void subtractComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, -1);
		ComplexNumber c2 = new ComplexNumber(-1, 1);
		assertEquals(new ComplexNumber(2, -2), c1.sub(c2));
	}
	
	@Test
	public void multiplyComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-1, -1);
		assertEquals(new ComplexNumber(0, -2), c1.mul(c2));
	}
	
	@Test
	public void divideComplexNumbers() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-1, -1);
		assertEquals(new ComplexNumber(-1, 0), c1.div(c2));
	}
	
	@Test
	public void divideComplexNumbersZeros() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber c2 = new ComplexNumber(0, 0);
		assertEquals(new ComplexNumber(0, 0), c1.div(c2));
	}
	
	@Test
	public void complexNumberPowerIllegalBase() {
		try {
			ComplexNumber c = new ComplexNumber(1, 1);
			c.power(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void complexNumbersPowerOfZero() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(new ComplexNumber(1, 0), c.power(0));
	}
	
	@Test
	public void complexNumbersPowerOfTwo() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(new ComplexNumber(0, 2), c.power(2));
	}
	
	@Test
	public void complexNumbersPowerOfThree() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(new ComplexNumber(-2, 2), c.power(3));
	}
	
	@Test
	public void rootIllegalArgument() {
		try {
			ComplexNumber c = new ComplexNumber(1, 1);
			c.root(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void squareRootOfComplexNumberFirstQuadrant() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(new ComplexNumber(1.09868411, 0.45508986), c.root(2)[0]);
		assertEquals(new ComplexNumber(-1.09868411, -0.45508986), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfComplexNumberSecondQuadrant() {
		ComplexNumber c = new ComplexNumber(-1, 1);
		assertEquals(new ComplexNumber(0.45508986, 1.09868411), c.root(2)[0]);
		assertEquals(new ComplexNumber(-0.45508986, -1.09868411), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfComplexNumberThirdQuadrant() {
		ComplexNumber c = new ComplexNumber(-1, -1);
		assertEquals(new ComplexNumber(-0.45508986, 1.09868411), c.root(2)[0]);
		assertEquals(new ComplexNumber(0.45508986, -1.09868411), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfComplexNumberFourthQuadrant() {
		ComplexNumber c = new ComplexNumber(1, -1);
		assertEquals(new ComplexNumber(-1.09868411, 0.45508986), c.root(2)[0]);
		assertEquals(new ComplexNumber(1.09868411, -0.45508986), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfNegativeOne() {
		ComplexNumber c = new ComplexNumber(-1, 0);
		assertEquals(new ComplexNumber(0, 1), c.root(2)[0]);
		assertEquals(new ComplexNumber(0, -1), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfImaginaryOne() {
		ComplexNumber c = new ComplexNumber(0, 1);
		assertEquals(new ComplexNumber(0.70710678, 0.70710678), c.root(2)[0]);
		assertEquals(new ComplexNumber(-0.70710678, -0.70710678), c.root(2)[1]);
	}
	
	@Test
	public void squareRootOfImaginaryNegativeOne() {
		ComplexNumber c = new ComplexNumber(0, -1);
		assertEquals(new ComplexNumber(-0.70710678, 0.70710678), c.root(2)[0]);
		assertEquals(new ComplexNumber(0.70710678, -0.70710678), c.root(2)[1]);
	}
	
	@Test
	public void firstRootOfComplexNumber() {
		ComplexNumber c = new ComplexNumber(0, -1);
		assertEquals(new ComplexNumber(0, -1), c.root(1)[0]);
	}
	
	@Test
	public void thirdRootOfComplexNumber() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(new ComplexNumber(1.08421508, 0.290514555), c.root(3)[0]);
		assertEquals(new ComplexNumber(-0.79370052, 0.793700052), c.root(3)[1]);
		assertEquals(new ComplexNumber(-0.290514555, -1.08421508), c.root(3)[2]);
	}
	
	@Test
	public void parseComplexNumber() {
		ComplexNumber c = ComplexNumber.parse("2.5-i");
		assertEquals(new ComplexNumber(2.5, -1), c);
	}
	
	@Test
	public void parseComplexBothPartsNegative() {
		ComplexNumber c = ComplexNumber.parse("-2.5-3i");
		assertEquals(new ComplexNumber(-2.5, -3), c);
	}
	
	@Test
	public void parsePureInteger() {
		ComplexNumber c = ComplexNumber.parse("2");
		assertEquals(new ComplexNumber(2, 0), c);
	}
	
	@Test
	public void parsePureDecimal() {
		ComplexNumber c = ComplexNumber.parse("2.233");
		assertEquals(new ComplexNumber(2.233, 0), c);
	}
	
	@Test
	public void parsePurePositiveIntegerImaginary() {
		ComplexNumber c = ComplexNumber.parse("i");
		assertEquals(new ComplexNumber(0, 1), c);
	}
	
	@Test
	public void parsePureNegativeIntegerImaginary() {
		ComplexNumber c = ComplexNumber.parse("-i");
		assertEquals(new ComplexNumber(0, -1), c);
	}
	
	@Test
	public void parsePurePositiveDecimalImaginary() {
		ComplexNumber c = ComplexNumber.parse("2.0i");
		assertEquals(new ComplexNumber(0, 2), c);
	}
	
	@Test
	public void parsePureNegativeDecimalImaginary() {
		ComplexNumber c = ComplexNumber.parse("-3.1i");
		assertEquals(new ComplexNumber(0, -3.1), c);
	}
	
	@Test
	public void parseImaginaryNumberLetterInFront() {
		try {
			ComplexNumber c = ComplexNumber.parse("-i3.1");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void parseComplexMultipleSigns() {
		try {
			ComplexNumber c = ComplexNumber.parse("-+i3.1");
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void parseRealIntegerLeadingPlusSign() {
		ComplexNumber c = ComplexNumber.parse("+3");
		assertEquals(new ComplexNumber(3, 0), c);
	}
	
}
