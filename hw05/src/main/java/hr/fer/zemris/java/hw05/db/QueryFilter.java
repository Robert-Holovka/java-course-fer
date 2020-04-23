package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Filters records from student database.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class QueryFilter implements IFilter {

	/**
	 * Query defined as a list of conditional expressions.
	 */
	private List<ConditionalExpression> queries;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param queries Query defined as a list of conditional expressions.
	 */
	public QueryFilter(List<ConditionalExpression> queries) {
		this.queries = queries;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression query : queries) {
			IComparisonOperator operator = query.getComparisonOperator();
			String argument = query.getFieldGetter().get(record);
			String literal = query.getStringLiteral();

			if (!operator.satisfied(argument, literal)) {
				return false;
			}
		}

		return true;
	}

}
