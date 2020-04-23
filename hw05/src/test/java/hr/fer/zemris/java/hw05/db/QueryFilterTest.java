package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {

	@Test
	public void queryFilterTrue() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.JMBAG, ComparisonOperators.EQUALS,
				"1");
		QueryFilter filter = new QueryFilter(Arrays.asList(expr));

		StudentRecord record = new StudentRecord("1", "nebitno", "nebitno", 1);
		assertTrue(filter.accepts(record));
	}

	@Test
	public void queryFilterFalse() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.FIRST_NAME, ComparisonOperators.LESS,
				"abc");
		QueryFilter filter = new QueryFilter(Arrays.asList(expr));

		StudentRecord record = new StudentRecord("1", "nebitno", "nebitno", 1);
		assertFalse(filter.accepts(record));
	}
}
