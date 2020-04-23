package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class <code>ComplexNumber</code> provides various methods for operating with
 * complex numbers. Instances are unmodifiable. Each operation that alters
 * original complex number will return new instance.
 * 
 * @author Robo
 * @version 1.0
 */
public class ComplexNumber {

	/**
	 * Maximum allowed difference between two real numbers.
	 */
	public static final double MAX_DIFFERENCE = 0.000001;
	/**
	 * Pattern for finding complex numbers that contains only real part.
	 */
	private static final String COMPLEX_NUMBER_REAL = "([+-]?\\d+(\\.\\d+)?)";
	/**
	 * Pattern for finding complex numbers that contains only imaginary part.
	 */
	private static final String COMPLEX_NUMBER_IMAGINARY = "([+-]?)(\\d+(\\.\\d+)?)?i";
	/**
	 * Pattern for finding complex numbers with both parts, real and imaginary.
	 */
	private static final String COMPLEX_NUMBER_FULL_FORM = COMPLEX_NUMBER_REAL + "([+-])((\\d+(\\.\\d+)?)?i)";

	/**
	 * Stores real part of a complex number.
	 */
	private double real;
	/**
	 * Stores imaginary part of a complex number.
	 */
	private double imaginary;

	/**
	 * Creates a new instance of a complex number with real and imaginary part set
	 * to the value of passed arguments.
	 * 
	 * @param real      Real part of complex number.
	 * @param imaginary Imaginary part of complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Creates a new instance of a complex number that contains only real part.
	 * Imaginary part is set to 0.
	 * 
	 * @param real Real part of a complex number
	 * @return New instance of the <code>ComplexNumber</code> class.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a new instance of a complex number that contains only imaginary part.
	 * Real part is set to 0.
	 * 
	 * @param real Real part of a complex number
	 * @return New instance of the <code>ComplexNumber</code> class.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Converts complex number from polar to rectangular form.
	 * 
	 * @param magnitude Magnitude of a complex number
	 * @param angle     Angle of a complex number
	 * @return New instance of the <code>ComplexNumber</code> class.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Creates a new instance of a complex number by parsing its String
	 * representation. Specifying only real or imaginary part is allowed. Passing
	 * only Strings "i" or "-i" is also allowed. Whitespace and multiple signs of
	 * the number are not allowed. Imaginary part must contain letter "i" at the
	 * end.
	 * 
	 * <p>
	 * Example of usage:
	 * </p>
	 * 
	 * <pre>
	 * ComplexNumber c1 = ComplexNumber.parse("-i");
	 * ComplexNumber c2 = ComplexNumber.parse("1");
	 * ComplexNumber c3 = ComplexNumber.parse("1+i");
	 * ComplexNumber c4 = ComplexNumber.parse("+1-3.0i");
	 * </pre>
	 * 
	 * @param s Complex number in String form
	 * @return New instance of the <code>ComplexNumber</code> class.
	 * @throws IllegalArgumentException If given String does not match correct
	 *                                  representation of complex number
	 */
	public static ComplexNumber parse(String s) {
		Pattern pattern = Pattern.compile(COMPLEX_NUMBER_FULL_FORM);
		Matcher matcher = pattern.matcher(s);

		// Check if String contains real and imaginary part
		if (matcher.matches()) {
			double real = Double.parseDouble(matcher.group(1));
			String sign = matcher.group(3);
			String imaginary = matcher.group(5);
			imaginary = imaginary == null ? "1" : imaginary; // Check for pure "i"

			return new ComplexNumber(real, Double.parseDouble(sign + imaginary));
		}

		pattern = Pattern.compile(COMPLEX_NUMBER_REAL);
		matcher = pattern.matcher(s);

		// Check if String contains only real part
		if (matcher.matches()) {
			double real = Double.parseDouble(matcher.group(1));
			return fromReal(real);
		}

		pattern = Pattern.compile(COMPLEX_NUMBER_IMAGINARY);
		matcher = pattern.matcher(s);

		// Check if String contains only imaginary part
		if (matcher.matches()) {
			String sign = matcher.group(1);
			String imaginary = matcher.group(2);
			imaginary = imaginary == null ? "1" : imaginary; // Check for pure "i"

			return fromImaginary(Double.parseDouble(sign + imaginary));
		}

		// String does not contain real or imaginary parts in correct form
		throw new IllegalArgumentException("Could not parse given String into complex number");
	}

	/**
	 * Returns real part of the complex number.
	 * 
	 * @return Real part of a complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of the complex number.
	 * 
	 * @return Imaginary part of a complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns magnitude of the complex number.
	 * 
	 * @return Magnitude of a complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns angle of the complex number.
	 * 
	 * @return Angle of a complex number.
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		if (angle < 0.0) {
			angle += 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Adds two complex numbers and returns new instance of the
	 * <code>ComplexNumber</code> class as result.
	 * 
	 * @param c Summand
	 * @return Result of addition as a new instance of the
	 *         <code>ComplexNumber</code> class.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Subtracts two complex numbers and returns new instance of the
	 * <code>ComplexNumber</code> class as result.
	 * 
	 * @param c Subtrahend
	 * @return Result of subtraction as a new instance of the
	 *         <code>ComplexNumber</code> class.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Multiplies two complex numbers and returns new instance of the
	 * <code>ComplexNumber</code> class as result.
	 * 
	 * @param c Multiplicand
	 * @return Result of multiplication as a new instance of the
	 *         <code>ComplexNumber</code> class.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double magnitude = getMagnitude() * c.getMagnitude();
		double angle = getAngle() + c.getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Divides two complex numbers and returns new instance of the
	 * <code>ComplexNumber</code> class as result.
	 * 
	 * @param c Denominator
	 * @return Result of division as a new instance of the
	 *         <code>ComplexNumber</code> class.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double magnitude = getMagnitude() / c.getMagnitude();
		double angle = getAngle() - c.getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns complex number raised to the power of the passed argument.
	 * 
	 * @param n Exponent of the power operation
	 * @return Result of the power operation as a new instance of the
	 *         <code>ComplexNumber</code> class.
	 * @throws IllegalArgumentException if exponent is less than 0.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(getMagnitude(), n);
		double angle = n * getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns nth roots of a complex number as an array of new instances of the
	 * <code>ComplexNumber</code> class.
	 * 
	 * @param n Degree of root operation
	 * @return Result of the root operation as an array of complex numbers
	 * @throws IllegalArgumentException if degree is less than 1
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(getMagnitude(), (double) 1 / n);
		double angle = getAngle();

		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			double real = magnitude * Math.cos((angle + 2 * Math.PI * i) / n);
			double imaginary = magnitude * Math.sin((angle + 2 * Math.PI * i) / n);
			roots[i] = new ComplexNumber(real, imaginary);
		}

		return roots;
	}

	/**
	 * Returns String representation of a complex number.
	 * 
	 * @return String representation of the complex number.
	 */
	@Override
	public String toString() {
		return real + " " + imaginary + "i";
	}

	/**
	 * Compares two instances of <code>ComplexNumber</code> class. Two instances are
	 * equal if the difference between their real and imaginary part is not greater
	 * than 10^-6.
	 * 
	 * @param obj Object to be compared with instance that called this method.
	 * @return True if instances are equal, false if instances are not equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComplexNumber other = (ComplexNumber) obj;
		if (imaginary - other.getImaginary() > MAX_DIFFERENCE) {
			return false;
		}
		if (real - other.getReal() > MAX_DIFFERENCE) {
			return false;
		}

		return true;
	}

}
