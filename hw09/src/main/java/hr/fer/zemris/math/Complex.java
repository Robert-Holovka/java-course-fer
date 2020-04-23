package hr.fer.zemris.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Complex provides various methods for operating with complex numbers.
 * Instances are unmodifiable. Each operation that alters original complex
 * number will return new instance.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Complex {

	/**
	 * Maximum allowed difference between two real numbers if they are considered as
	 * equal.
	 */
	private static final double MAX_DIFFERENCE = 10E-6;

	/**
	 * Pattern for finding complex numbers that contains only real part.
	 */
	private static final String COMPLEX_NUMBER_REAL = "([+-]?\\d+(\\.\\d+)?)";
	/**
	 * Pattern for finding complex numbers that contains only imaginary part.
	 */
	private static final String COMPLEX_NUMBER_IMAGINARY = "([+-]?)i(\\d+(\\.\\d+)?)?";
	/**
	 * Pattern for finding complex numbers with both parts, real and imaginary.
	 */
	private static final String COMPLEX_NUMBER_FULL_FORM = COMPLEX_NUMBER_REAL + "([+-])i((\\d+(\\.\\d+)?)?)";

	/**
	 * Cached complex number: 0 + 0i
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Cached complex number: 1 + 0i
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Cached complex number: -1 + 0i
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Cached complex number: 0 + i
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Cached complex number: 0 - i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Stores real part of a complex number.
	 */
	private double real;
	/**
	 * Stores imaginary part of a complex number.
	 */
	private double imaginary;

	/**
	 * Creates default complex number.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates a new instance of a complex number with real and imaginary part set
	 * to the value of passed arguments.
	 * 
	 * @param re Real part of complex number.
	 * @param im Imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * Returns real part of this complex number.
	 * 
	 * @return real
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of this complex number.
	 * 
	 * @return imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns module of this complex number.
	 * 
	 * @return module
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Multiplies two complex numbers and returns new instance of the Complex class
	 * as result.
	 * 
	 * @param c Multiplicand
	 * @return Result of multiplication as a new instance of the Complex class.
	 */
	public Complex multiply(Complex c) {
		double r = real * c.real - imaginary * c.imaginary;
		double i = real * c.imaginary + imaginary * c.real;
		return new Complex(r, i);
	}

	/**
	 * Divides two complex numbers and returns new instance of the Complex class as
	 * result.
	 * 
	 * @param c Denominator
	 * @return Result of division as a new instance of the Complex class.
	 */
	public Complex divide(Complex c) {
		double r = real * c.real + imaginary * c.imaginary;
		double i = imaginary * c.real - real * c.imaginary;
		double divisor = c.real * c.real + c.imaginary * c.imaginary;
		return new Complex(r / divisor, i / divisor);
	}

	/**
	 * Adds two complex numbers and returns new instance of the Complex class as
	 * result.
	 * 
	 * @param c Summand
	 * @return Result of addition as a new instance of the Complex class.
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Subtracts two complex numbers and returns new instance of the Complex class
	 * as result.
	 * 
	 * @param c Subtrahend
	 * @return Result of subtraction as a new instance of the Complex class.
	 */
	public Complex sub(Complex c) {
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Negates this complex number and returns new instance of the Complex class as
	 * a result.
	 * 
	 * @return negated
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns complex number raised to the power of the passed argument.
	 * 
	 * @param n Exponent of the power operation
	 * @return Result of the power operation as a new instance of the Complex class.
	 * @throws IllegalArgumentException if exponent is less than 0.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(module(), n);
		double angle = n * angle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns nth roots of a complex number as a List of new instances of the
	 * Complex class.
	 * 
	 * @param n Degree of root operation
	 * @return Result of the root operation as a List of complex numbers
	 * @throws IllegalArgumentException if degree is less than 1
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(module(), (double) 1 / n);
		double angle = angle();

		LinkedList<Complex> roots = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			double re = magnitude * Math.cos((angle + 2 * Math.PI * i) / n);
			double im = magnitude * Math.sin((angle + 2 * Math.PI * i) / n);
			roots.add(new Complex(re, im));
		}

		return roots;
	}

	@Override
	public String toString() {
		real = round(real, 6);
		imaginary = round(imaginary, 6);
		char sign = (imaginary >= 0) ? '+' : '-';
		return "(" + real + sign + "i" + Math.abs(imaginary) + ')';
	}

	/**
	 * Returns angle of the complex number.
	 * 
	 * @return Angle of a complex number.
	 */
	private double angle() {
		double angle = Math.atan2(imaginary, real);
		if (angle < 0.0) {
			angle += 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Converts complex number from polar to rectangular form.
	 * 
	 * @param magnitude Magnitude of a complex number
	 * @param angle     Angle of a complex number
	 * @return New instance of the Complex class.
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Rounds given decimal number to the specified number of digits.
	 * 
	 * @param value  Decimal to be rounded
	 * @param places
	 * @return Rounded decimal
	 */
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Creates a new instance of a complex number by parsing its String
	 * representation. Specifying only real or imaginary part is allowed. Passing
	 * only Strings "i" or "-i" is also allowed.
	 * 
	 * <p>
	 * Example of usage:
	 * </p>
	 * 
	 * <pre>
	 * ComplexNumber c1 = ComplexNumber.parse("-i");
	 * ComplexNumber c2 = ComplexNumber.parse("1");
	 * ComplexNumber c3 = ComplexNumber.parse("1 + i");
	 * ComplexNumber c4 = ComplexNumber.parse("+1 - i3");
	 * </pre>
	 * 
	 * @param s Complex number in String form
	 * @return New instance of the <code>ComplexNumber</code> class.
	 * @throws IllegalArgumentException If given String does not match correct
	 *                                  representation of complex number
	 */
	public static Complex parse(String s) {
		s = s.replaceAll("\\s+", "");
		Pattern pattern = Pattern.compile(COMPLEX_NUMBER_FULL_FORM);
		Matcher matcher = pattern.matcher(s);

		// Check if String contains real and imaginary part
		if (matcher.matches()) {
			double real = Double.parseDouble(matcher.group(1));
			String sign = matcher.group(3);
			String imaginary = matcher.group(5);
			imaginary = imaginary == null ? "1" : imaginary; // Check for pure "i"

			return new Complex(real, Double.parseDouble(sign + imaginary));
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
	 * Creates a new instance of a complex number that contains only real part.
	 * Imaginary part is set to 0.
	 * 
	 * @param real Real part of a complex number
	 * @return New instance of the Complex class.
	 */
	public static Complex fromReal(double real) {
		return new Complex(real, 0);
	}

	/**
	 * Creates a new instance of a complex number that contains only imaginary part.
	 * Real part is set to 0.
	 * 
	 * @param real Real part of a complex number
	 * @return New instance of the Complex class.
	 */
	public static Complex fromImaginary(double imaginary) {
		return new Complex(0, imaginary);
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		return Math.abs(imaginary - other.imaginary) < MAX_DIFFERENCE
				&& Math.abs(real - other.real) < MAX_DIFFERENCE;
	}

}
