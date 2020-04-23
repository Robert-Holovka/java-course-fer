package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Wrapper for value that ObjectMultistack stores. Defines some basic arithmetic
 * operations that can be performed on an instance of this class. Allowed
 * operations are addition, subtraction, multiplication and division. This class
 * can store any type but operations can only be performed on these types: null,
 * String, Integer and Double.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ValueWrapper {

	/**
	 * Storage for value.
	 */
	private Object value;

	/**
	 * Constructs instance of this class defined by a given value.
	 * 
	 * @param value Object
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns stored vale.
	 * 
	 * @return Object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Stores new value.
	 * 
	 * @param value Object given value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Performs addition on value that this instance stores with given value.
	 * 
	 * @param incValue Second operand
	 */
	public void add(Object incValue) {
		performOperation(incValue, (arg1, arg2) -> arg1 + arg2);
	}

	/**
	 * Performs subtraction on value that this instance stores with given value.
	 * 
	 * @param decValue Second operand
	 */
	public void subtract(Object decValue) {
		performOperation(decValue, (arg1, arg2) -> arg1 - arg2);
	}

	/**
	 * Performs multiplication on value that this instance stores with given value.
	 * 
	 * @param mulValue Second operand
	 */
	public void multiply(Object mulValue) {
		performOperation(mulValue, (arg1, arg2) -> arg1 * arg2);
	}

	/**
	 * Performs division on value that this instance stores with given value.
	 * 
	 * @param divValue Second operand
	 */
	public void divide(Object divValue) {
		performOperation(divValue, (arg1, arg2) -> arg1 / arg2);
	}

	/**
	 * Performs numerical comparison between the currently stored value in the
	 * ValueWrapper object and given argument. Promotes both arguments to the same
	 * type before comparing. The method returns an Integer less than zero if the
	 * currently stored value is smaller than the argument, an integer greater than
	 * zero if the currently stored value is larger than the argument, or an integer
	 * 0 if they are equal.
	 * 
	 * @param withValue Value to be compared with
	 * @return 0 if values are equal, less than zero if currently stored value is
	 *         lower than given one, greater then zero if currently stored value is
	 *         greater than given one
	 * @throws IllegalArgumentException if first or second operand is not one of the
	 *                                  valid types: null, Integer, String or
	 *                                  Double.
	 * @throws NumberFormatException    if one of the operands is a String which
	 *                                  does not contain textual representation of a
	 *                                  Number
	 */
	public int numCompare(Object withValue) {
		if (!isValidType(value) || !isValidType(withValue)) {
			throw new IllegalArgumentException(String.format("Comparation can not be performed on a types %s and %s.",
					value.getClass(), withValue.getClass()));
		}

		Object firstOperand = (value == null) ? Integer.valueOf(0) : value;
		withValue = (withValue == null) ? Integer.valueOf(0) : withValue;

		firstOperand = (firstOperand instanceof String) ? extractNumber((String) firstOperand) : firstOperand;
		withValue = (withValue instanceof String) ? extractNumber((String) withValue) : withValue;

		if (value instanceof Double || withValue instanceof Double) { 
			Double first = Double.valueOf(firstOperand.toString());
			Double second = Double.valueOf(withValue.toString());
			return first.compareTo(second);
		} else {
			Integer first = Integer.valueOf(firstOperand.toString());
			Integer second = Integer.valueOf(withValue.toString());
			return first.compareTo(second);
		}

	}

	/**
	 * Performs given {@code operation} on an instance of this class and given
	 * value. Null reference is transformed to the Integer with value 0. String is
	 * parsed to Double or Integer and if it can't be parsed exception is thrown. If
	 * any of operands is a type of a Double result will be also Double, otherwise
	 * result will be returned as Integer.
	 * 
	 * @param secondOperand Object
	 * @param operation     BiFunction Addition, subtraction, multiplication or
	 *                      division
	 * @throws IllegalArgumentException if first or second operand is not one of the
	 *                                  valid types: null, Integer, String or
	 *                                  Double.
	 * @throws NumberFormatException    if one of the operands is a String which
	 *                                  does not contain textual representation of a
	 *                                  Number
	 */
	private void performOperation(Object secondOperand, BiFunction<Double, Double, Double> operation) {
		if (!isValidType(value) || !isValidType(secondOperand)) {
			throw new IllegalArgumentException(String.format("Operation can not be performed on a types %s and %s.",
					value.getClass(), secondOperand.getClass()));
		}

		Object firstOperand = (value == null) ? Integer.valueOf(0) : value;
		secondOperand = (secondOperand == null) ? Integer.valueOf(0) : secondOperand;

		firstOperand = (firstOperand instanceof String) ? extractNumber((String) firstOperand) : firstOperand;
		secondOperand = (secondOperand instanceof String) ? extractNumber((String) secondOperand) : secondOperand;

		Double result = operation.apply(Double.valueOf(firstOperand.toString()),
				Double.valueOf(secondOperand.toString()));

		if (firstOperand instanceof Double || secondOperand instanceof Double
				|| !result.toString().matches("-?\\d+(\\.0)")) { // Division of two Integers can produce Double
			value = result;
		} else {
			value = result.intValue();
		}
	}

	/**
	 * Extracts Integer or Double from a given string.
	 * 
	 * @param number Text representation of a number.
	 * @return Double or Integer
	 * @throws NumberFormatException if given String is not textual representation
	 *                               of a Number
	 */
	private Object extractNumber(String number) {
		if (number.matches("^-?[0-9]+$")) {
			return Integer.parseInt(number);
		}
		if (number.matches("-?\\d+(\\.\\d+)?(E-?\\d+)?")) {
			return Double.parseDouble(number);
		}
		throw new NumberFormatException("One of the operands is a type of String but does not contain number.");
	}

	/**
	 * Checks whether given object is a valid type. Valid types are String, Integer,
	 * Double and null reference.
	 * 
	 * @param obj Object to be checked
	 * @return True if given Object is a valid type, false otherwise
	 */
	private boolean isValidType(Object obj) {
		return obj == null || obj instanceof String || obj instanceof Integer || obj instanceof Double;
	}
}
