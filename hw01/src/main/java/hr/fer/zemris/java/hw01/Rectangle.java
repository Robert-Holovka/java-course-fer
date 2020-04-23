package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program calculates area and perimeter of a rectangle from given width and
 * height. User can pass 0 or 2 arguments when starts this program, otherwise
 * program will terminate and print appropriate message to the user. Arguments
 * must be positive numbers(integers or decimals).
 * 
 * If number of passed arguments is equal to 2, width is considered as first
 * argument and height as second and program will automatically calculate area
 * and perimeter and print back final result. After that, program will
 * terminate.
 * 
 * If number of passed arguments is equal to 0, program will ask user to type in
 * width and height through its interface. Firstly, it will ask user to type
 * width of a rectangle and will repeat that while given input is not equal to a
 * positive number. After that, it will prompt message asking for height and it
 * will repeat that message while user input is not equal to a positive number.
 * After 2 positive numbers were given, program will calculate area and
 * perimeter of a rectangle and print back final result to the user and finally
 * it will terminate.
 * 
 * <p>
 * Example of usage with 2 arguments:
 * </p>
 * 
 * <pre>
 * java Rectangle 2 8
 * output: Pravokutnik širine 2.0 i visine 8.0 ima površinu 16.0 te opseg 20.0
 * </pre>
 * 
 * <p>
 * Example of usage with 0 arguments:
 * </p>
 * 
 * <pre>
 * java Rectangle
 * output: Unesite širinu >
 * input: -1
 * output: Unijeli ste negativnu vrijednost.
 * output: Unesite širinu >
 * input: 2
 * output: Unesite visinu >
 * input: 8
 * output: Pravokutnik širine 2.0 i visine 8.0 ima površinu 16.0 te opseg 20.0
 * </pre>
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class Rectangle {

	/**
	 * Entry point of the program. Number of arguments allowed is 0 or 2. Arguments
	 * must be positive numbers. First argument represents width, second height.
	 * 
	 * @param args Arguments from command line
	 */
	public static void main(String[] args) {
		if (args.length != 0 && args.length != 2) {
			System.out.println(
					"Neispravan broj argumenata, " + "potrebno je unijeti širinu pa potom visinu pravokutnika.");
			return;
		}

		double width = 0.0;
		double height = 0.0;
		if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);
			width = loadArgument("Unesite širinu > ", scanner);
			height = loadArgument("Unesite visinu > ", scanner);
			scanner.close();
		} else {
			boolean isWidthValid = true;
			try {
				width = parsePositiveDouble(args[0]);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				isWidthValid = false;
			}
			try {
				height = parsePositiveDouble(args[1]);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				return;
			}
			if (!isWidthValid) {
				return;
			}
		}
		printResult(width, height);
	}

	/**
	 * Reads user inputs from keyboard. It will repeatedly prompt given message
	 * while input does not match positive number. Once user has entered correct
	 * input, it will return given number value stored as <code>double</code> type.
	 * 
	 * @param Message Instruction for the user.
	 * @param Scanner Class responsible for fetching user input.
	 * @return double Parsed number value.
	 */
	public static double loadArgument(String message, Scanner scanner) {
		double d;
		while (true) {
			System.out.print(message);
			String argument = scanner.next();
			try {
				d = parsePositiveDouble(argument);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		return d;
	}

	/**
	 * Method prints out final result to the user, area and perimeter of a triangle.
	 * 
	 * @param width  Width of a rectangle.
	 * @param height Height of a rectangle.
	 */
	public static void printResult(double width, double height) {
		Double perimeter = calculatePerimeter(width, height);
		Double area = calculateArea(width, height);

		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + area.toString()
				+ " te opseg " + perimeter.toString() + ".");
	}

	/**
	 * Calculates perimeter of a rectangle.
	 * 
	 * @param Width  Width of the rectangle
	 * @param Height Height of the rectangle
	 * @return Perimeter
	 */
	public static double calculatePerimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * Calculates area of a rectangle.
	 * 
	 * @param Width  Width of the rectangle
	 * @param Height Height of the rectangle
	 * @return Area
	 */
	public static double calculateArea(double width, double height) {
		return width * height;
	}

	/**
	 * Converts given {@link String} into a positive real number and returns it as a
	 * double value. If <code>String</code> does not contain such number exception
	 * is thrown with an appropriate message.
	 * 
	 * @param s String which should contain positive real number
	 * @return double positive real number
	 * @throws {@link IllegalArgumentException} if passed <code>String</code> does
	 *         not contain positive real number.
	 */
	public static double parsePositiveDouble(String s) {
		try {
			double d = Double.parseDouble(s);
			if (d < 0.0) {
				throw new IllegalArgumentException("Unijeli ste negativnu vrijednost.");
			}
			return d;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(String.format("'%s' se ne može protumačiti kao broj.", s));
		}
	}
}
