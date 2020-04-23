package hr.fer.zemris.math;

/**
 * Models complex polynomial as an array of factors. Example: (7+2i)z^3 + 2z^2 +
 * 5z + 1.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ComplexPolynomial {

	/**
	 * Factors of this polynomial.
	 */
	Complex[] factors;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns order of this polynomial.
	 * 
	 * @return order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies this polynomial with a given one. Result is returned as an new
	 * instance of this class.
	 * 
	 * @param p multiplicand
	 * @return result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				Complex product = factors[i].multiply(p.factors[j]);
				int order = i + j;
				if (newFactors[order] == null) {
					newFactors[order] = product;
				} else {
					newFactors[order] = newFactors[order].add(product);
				}
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Returns derivative of this polynomial.
	 * 
	 * @return derivative
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];

		for (int i = factors.length - 1; i > 0; i--) {
			Complex temp = factors[i];
			newFactors[i - 1] = new Complex(temp.getReal() * i, temp.getImaginary() * i);
		}

		if (newFactors.length == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Calculates value of this polynomial for a given z;
	 * 
	 * @param z Complex number
	 * @return result
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		Complex cachePowerZ = Complex.ONE;

		int len = factors.length;
		for (int i = 0; i < len; i++) {
			result = result.add(factors[i].multiply(cachePowerZ));
			cachePowerZ = cachePowerZ.multiply(z);
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append(factors[i]);
			if (i != 0) {
				sb.append('*');
				sb.append("z^" + i);
				sb.append('+');
			}
		}
		return sb.toString();
	}
}