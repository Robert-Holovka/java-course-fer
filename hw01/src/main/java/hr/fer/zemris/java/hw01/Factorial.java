package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program computes factorial of a given number. Valid range for a number is
 * [3,20]. Program will repeatedly ask user to input new number with keyboard if
 * previous one was not from specified range. After valid number is entered
 * program will calculate its factorial and print out result.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class Factorial {

	/*
	 * Minimal value for which factorial can be computed.
	 */
	private static final int MIN_FACTORIAL_INPUT = 0;
	/*
	 * Maximum value for which factorial can be computed.
	 */
	private static final int MAX_FACTORIAL_INPUT = 20;

	/**
	 * Entry point of the program. Asks user to type a number for which he wants to
	 * compute factorial. Valid range for numbers is [3, 20].
	 * 
	 * @param args arguments from the command line
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");
			String line = scanner.next();

			if (line.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int n = Integer.parseInt(line);
				if (n < 3 || n > MAX_FACTORIAL_INPUT) {
					System.out.format("'%s' nije broj u dozvoljenom rasponu.%n", line);
				} else {
					long factorial = calculateFactorial(n);
					System.out.format("%d! = %d%n", n, factorial);
				}
			} catch (IllegalArgumentException e) {
				System.out.format("'%s' nije cijeli broj.%n", line);
			}
		}
		scanner.close();
	}

	/**
	 * Computes factorial of a given number. Method can only calculate factorial for
	 * numbers from range [0, 20]. If passed number is outside of that range
	 * exception is thrown.
	 * 
	 * @param n Number for which factorial will be computed
	 * @return Calculated factorial for a given number
	 * @throws {@link IllegalArgumentException} If number for which factorial should
	 *         be calculated is outside the range [0, 20]
	 */
	public static long calculateFactorial(int n) {
		if (n < MIN_FACTORIAL_INPUT || n > MAX_FACTORIAL_INPUT) {
			throw new IllegalArgumentException();
		}

		long factorial = 1;
		for (int i = 2; i <= n; i++) {
			factorial = factorial * i;
		}

		return factorial;
	}
}
