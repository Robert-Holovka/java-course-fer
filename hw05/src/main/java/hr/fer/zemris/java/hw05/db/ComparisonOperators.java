package hr.fer.zemris.java.hw05.db;

/**
 * Contains concrete implementations of comparison operators.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ComparisonOperators {

	/**
	 * Checks whether first argument is less than second.
	 */
	public static final IComparisonOperator LESS = (arg1, arg2) -> {
		return arg1.compareTo(arg2) < 0;
	};
	/**
	 * Checks whether first argument is less than second or if they are equal.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (arg1, arg2) -> {
		return arg1.compareTo(arg2) <= 0;
	};
	/**
	 * Checks whether first argument is greater than second.
	 */
	public static final IComparisonOperator GREATER = (arg1, arg2) -> {
		return arg1.compareTo(arg2) > 0;
	};
	/**
	 * Checks whether first argument is greater than second or if they are equal.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (arg1, arg2) -> {
		return arg1.compareTo(arg2) >= 0;
	};
	/**
	 * Checks if arguments are equal.
	 */
	public static final IComparisonOperator EQUALS = (arg1, arg2) -> {
		return arg1.compareTo(arg2) == 0;
	};
	/**
	 * Checks if arguments are not equal.
	 */
	public static final IComparisonOperator NOT_EQUALS = (arg1, arg2) -> {
		return arg1.compareTo(arg2) != 0;
	};

	/**
	 * Checks if first argument satisfies some given pattern.
	 */
	public static final IComparisonOperator LIKE = (arg, pattern) -> {
		pattern = pattern.replaceAll("\\*", ".*");
		pattern = "^" + pattern + "$";
		return arg.matches(pattern);
	};
}
