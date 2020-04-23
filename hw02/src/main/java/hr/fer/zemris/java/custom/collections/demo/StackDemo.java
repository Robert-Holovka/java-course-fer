package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstrates usage of {@link ObjectStack} class. Program takes single
 * argument from command line which contains expression represented by postfix
 * notation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Contains a few simple operators that can be performed on integers
	 */
	private static final String[] OPERATORS = { "+", "-", "/", "*", "%" };

	/**
	 * Entry point of the program. This program should take only one argument from
	 * command line which contains expression represented by postfix notation. Whole
	 * expression must be enclosed by quotation marks. Integers and operators must
	 * be separated by whitespace.
	 * 
	 * <p>
	 * Example of usage:
	 * </p>
	 * 
	 * <pre>
	 * java StackDemo "-1 8 2 / +"
	 * </pre>
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Wrong number of arguments, expected '1' argument only!");
			return;
		}

		String[] inputs;
		try {
			inputs = parseLine(args[0]);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		try {
			System.out.println(calculateExpression(inputs));
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Performs given operations on two passed integers.
	 * 
	 * @param n1        First integer
	 * @param n2        Second integer
	 * @param operation Operation to be performed on integers
	 * @return Result of the operation
	 * @throws {@link ArithmeticException} if second integer is equal to zero
	 */
	private static int performOperation(int n1, int n2, String operator) {
		switch (operator) {
		case "+":
			return n1 + n2;
		case "-":
			return n1 - n2;
		case "*":
			return n1 * n2;
		case "/":
			if (n2 == 0) {
				throw new ArithmeticException("Division by zero is not allowed.");
			}
			return n1 / n2;
		default:
			return n1 % n2;
		}
	}

	/**
	 * Splits given line into integers and operators, then returns them as an array
	 * of strings.
	 * 
	 * @param line Line to be splitted
	 * @return String array which contains integers and operators
	 * @throws IllegalArgumentException if String contains something else then
	 *                                  integers or allowed operators(+, -, *, /, %)
	 */
	private static String[] parseLine(String line) {
		String inputs[] = line.split(" ");
		// Check if expression contains only integers and operators
		for (String input : inputs) {
			if (!isInteger(input) && !isOperation(input)) {
				throw new IllegalArgumentException("Expression is not valid.");
			}
		}

		return inputs;
	}

	/**
	 * Calculates given expression and returns the result.
	 * 
	 * @param inputs String array of integers and operators
	 * @return integer result
	 * @throws {@link ArithmeticException} if given expression could not be
	 *         evaluated(Division by zero etc.)
	 */
	private static int calculateExpression(String[] inputs) {
		ObjectStack stack = new ObjectStack();
		for (String input : inputs) {
			if (isInteger(input)) {
				stack.push(Integer.parseInt(input));
			} else if (isOperation(input)) {
				// Perform operation
				int n2 = (int) stack.pop();
				int n1 = (int) stack.pop();

				try {
					stack.push(performOperation(n1, n2, input));
				} catch (Exception e) {
					if (e instanceof ArithmeticException) {
						throw e;
					} else {
						throw new ArithmeticException("Result can not be evaluted for given expression");
					}
				}

			}
		}
		if (stack.size() != 1) {
			throw new ArithmeticException("Result can not be evaluted for given expression");
		} else {
			return (int) stack.pop();
		}
	}

	/**
	 * Checks if given {@link String} is an {@link Integer}.
	 * 
	 * @param input String that potentially is <code>Integer</code>
	 * @return True if given <code>String</code> can be parsed to
	 *         <code>Integer</code>, otherwise false
	 */
	private static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if given {@link String} is some of the allowed operations(+, -, /, *,
	 * %).
	 * 
	 * @param input String that potentially is operator
	 * @return True if given <code>String</code> can be parsed to valid operator,
	 *         otherwise false
	 */
	private static boolean isOperation(String input) {
		if (input.length() != 1) {
			return false;
		}

		for (String operator : OPERATORS) {
			if (operator.equals(input)) {
				return true;
			}
		}

		return false;
	}

}
