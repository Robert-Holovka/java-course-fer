package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Demonstrates usage of Complex, ComplexPolynomial & ComplexRootedPolynomial.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ComplexPolynomialDemo {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
//		Daje okvirni ispis:
//		(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))
//		(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)
//		(8.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(0.0+i0.0)
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());

		// Dodatni primjer
		ComplexRootedPolynomial crp2 = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp2 = crp2.toComplexPolynom();
		System.out.println(cp2);
		System.out.println(cp2.derive());
	}
}
