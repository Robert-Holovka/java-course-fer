package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	public void constructParser() {
		try {
			@SuppressWarnings("unused")
			var parser = new QueryParser("firstName = \"1\"");
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void constructParserNull() {
		assertThrows(QueryParserException.class, () -> new QueryParser(null));
	}

	@Test
	public void isDirectQueryTrue() {
		var parser = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(parser.isDirectQuery());
	}

	@Test
	public void isDirectQueryFalse() {
		var parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(parser.isDirectQuery());
	}

	@Test
	public void getQueriedJMBAG() {
		var parser = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals("0123456789", parser.getQueriedJMBAG());
	}
	
	@Test
	public void getQueriedJMBAGException() {
		var parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
	}

	@Test
	public void getQuery() {
		var parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(2, parser.getQuery().size());
	}
	
	@Test
	public void parseInvalidQuery() {
		assertThrows(QueryParserException.class, () -> new QueryParser(" \"1\" =\"0123456789\" "));
	}
	
	@Test
	public void parseInvalidQuery2() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=\"0123456789\"  lastName>\"J\""));
	}
	
	@Test
	public void parseInvalidQuery3() {
		assertThrows(QueryParserException.class, () -> new QueryParser("jbag=\"0123456789\"  lastName>\"J\""));
	}

}
