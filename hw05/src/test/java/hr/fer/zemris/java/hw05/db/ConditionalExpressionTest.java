package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	@Test
	public void conditionalTrue() {
		StudentRecord record = new StudentRecord("123", "Bos", "Ime", 3);
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, ComparisonOperators.LIKE,
				"Bos*");

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());

		assertTrue(recordSatisfies);
	}

	@Test
	public void conditionalFalse() {
		StudentRecord record = new StudentRecord("123", "Bosss", "Ime", 3);
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, ComparisonOperators.LIKE,
				"Bos*a");

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());

		assertFalse(recordSatisfies);
	}

}
