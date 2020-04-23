package hr.fer.zemris.math;

/**
 * Models complex polynomial as an array of roots and base constant. Example:
 * z0*(z-z1)*(z-z2)*...*(z-zn)
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ComplexRootedPolynomial {

	/**
	 * Constant z0.
	 */
	Complex constant;
	/**
	 * Roots of this polynomial.
	 */
	Complex[] roots;

	/**
	 * Constructs new polynomial defined by a given constant and roots.
	 * 
	 * @param constant z0
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.roots = roots;
		this.constant = constant;
	}

	/**
	 * Calculates value of this polynomial for a given z;
	 * 
	 * @param z Complex number
	 * @return result
	 */
	public Complex apply(Complex z) {
		if (roots.length == 0) {
			return constant;
		}

		Complex[] results = new Complex[roots.length];
		int len = roots.length;
		for (int i = 0; i < len; i++) {
			results[i] = constant.multiply(z).sub(constant.multiply(roots[i]));
		}

		Complex value = constant;
		for (Complex c : results) {
			value = value.multiply(c);
		}

		return value;
	}

	/**
	 * Transforms this polynomial to a from represented by ComplexPolynomial class.
	 * 
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial[] polynomials = new ComplexPolynomial[roots.length];

		int len = roots.length;
		for (int i = 0; i < len; i++) {
			polynomials[i] = new ComplexPolynomial(roots[i], Complex.ONE);
		}

		ComplexPolynomial result = new ComplexPolynomial(constant);
		for (ComplexPolynomial polynomial : polynomials) {
			result = result.multiply(polynomial);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant);
		sb.append('*');

		int len = roots.length;
		for (int i = 0; i < len; i++) {
			sb.append("(z-");
			sb.append(roots[i]);
			sb.append(')');
			if (i != (len - 1)) {
				sb.append('*');
			}
		}
		return sb.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold. If there is no such root, returns -1.
	 * 
	 * @param z        Complex
	 * @param treshold double
	 * @return index
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double closestMagnitude = Double.MAX_VALUE;
		int indexOfClosest = -1;

		int len = roots.length;
		for (int i = 0; i < len; i++) {
			double tempMagnitude = Math.abs(z.sub(roots[i]).module());
			if (tempMagnitude < treshold && tempMagnitude < closestMagnitude) {
				indexOfClosest = i;
				closestMagnitude = tempMagnitude;
			}
		}

		return indexOfClosest;
	}
}
