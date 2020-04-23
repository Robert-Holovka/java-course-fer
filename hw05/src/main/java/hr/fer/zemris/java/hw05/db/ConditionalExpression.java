package hr.fer.zemris.java.hw05.db;

/**
 * Represents unit of a query.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ConditionalExpression {

	/**
	 * Getter for some attribute from StudentRecord.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * Pattern.
	 */
	private String stringLiteral;
	/**
	 * Operator for comparing {@code #stringLiteral} and attribute from
	 * StudentRecord.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructs new instance of this class
	 * 
	 * @param fieldGetter        Getter for some attribute from StudentRecord
	 * @param comparisonOperator Operator for comparing {@code #stringLiteral} and
	 *                           attribute from StudentRecord
	 * @param stringLiteral      Pattern
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, IComparisonOperator comparisonOperator,
			String stringLiteral) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns {@code #fieldGetter}
	 * 
	 * @return IFieldValueGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns {@code #stringLiteral}
	 * 
	 * @return String
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns {@code #comparisonOperator}
	 * 
	 * @return IComparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
