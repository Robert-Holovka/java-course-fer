package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void isLessTrue() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isLessFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isLessFalse2() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void isLessOrEqualTrue() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isLessOrEqualTrue2() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isLessOrEqualFalse() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertFalse(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void isEqualTrue() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isEqualFalse() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isNotEqualTrue() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isNotEqualFalse() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isGreaterTrue() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void isGreaterFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isGreaterFalse2() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isGreaterOrEqualTrue() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}

	@Test
	public void isGreaterOrEqualTrue2() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isGreaterOrEqualFalse() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void isLikeTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Ana", "Ana"));
	}

	@Test
	public void isLikeTrue2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}

	@Test
	public void isLikeTrue3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AABAA", "AA*AA"));
	}

	@Test
	public void isLikeTrue4() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAAA", "AA*AA"));
	}

	@Test
	public void isLikeFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "AbA"));
	}

	@Test
	public void isLikeFalse3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}

}
