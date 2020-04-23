package hr.fer.zemris.java.hw05.db.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LexerTest {

	@Test
	public void nextTokenAfterEOL() {
		Lexer lexer = new Lexer("\"123\"");
		lexer.nextToken();
		lexer.nextToken();
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void extractSimpleLiteral() {
		Lexer lexer = new Lexer("\"123\"");
		Token literal = new Token(TokenType.STRING_LITERAL, "123");
		Token eol = new Token(TokenType.EOL, null);

		assertEquals(literal, lexer.nextToken());
		assertEquals(eol, lexer.nextToken());
	}

	@Test
	public void extractComplexLiteral() {
		Lexer lexer = new Lexer("\n	\"123asdasd.*023123das.asd21sad.*\"		\n	");
		Token literal = new Token(TokenType.STRING_LITERAL, "123asdasd.*023123das.asd21sad.*");
		Token eol = new Token(TokenType.EOL, null);

		assertEquals(literal, lexer.nextToken());
		assertEquals(eol, lexer.nextToken());
	}

	@Test
	public void extractWord() {
		Lexer lexer = new Lexer("  something  \n\t");
		Token word = new Token(TokenType.WORD, "something");
		Token eol = new Token(TokenType.EOL, null);

		assertEquals(word, lexer.nextToken());
		assertEquals(eol, lexer.nextToken());
	}

	@Test
	public void extractSpecialCharacter() {
		Lexer lexer = new Lexer("  <  ");
		Token word = new Token(TokenType.SPECIAL_CHARACTERS, "<");
		Token eol = new Token(TokenType.EOL, null);

		assertEquals(word, lexer.nextToken());
		assertEquals(eol, lexer.nextToken());
	}

	@Test
	public void extractSpecialCharacters() {
		Lexer lexer = new Lexer(" <==><=  \n\t");
		Token word = new Token(TokenType.SPECIAL_CHARACTERS, "<==><=");
		Token eol = new Token(TokenType.EOL, null);

		assertEquals(word, lexer.nextToken());
		assertEquals(eol, lexer.nextToken());
	}

	@Test
	public void extractQuery() {
		Lexer lexer = new Lexer("jmbag=\"0000000003\"");

		Token correctData[] = { new Token(TokenType.WORD, "jmbag"), new Token(TokenType.SPECIAL_CHARACTERS, "="),
				new Token(TokenType.STRING_LITERAL, "0000000003"), new Token(TokenType.EOL, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void extractQuery2() {
		Lexer lexer = new Lexer("lastName = \"Blažić\"");

		Token correctData[] = { new Token(TokenType.WORD, "lastName"), new Token(TokenType.SPECIAL_CHARACTERS, "="),
				new Token(TokenType.STRING_LITERAL, "Blažić"), new Token(TokenType.EOL, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void extractQuery3() {
		Lexer lexer = new Lexer(" firstName>\"A\" and lastName LIKE \"B*ć\"");

		Token correctData[] = { new Token(TokenType.WORD, "firstName"), new Token(TokenType.SPECIAL_CHARACTERS, ">"),
				new Token(TokenType.STRING_LITERAL, "A"), new Token(TokenType.WORD, "and"),
				new Token(TokenType.WORD, "lastName"), new Token(TokenType.WORD, "LIKE"),
				new Token(TokenType.STRING_LITERAL, "B*ć"), new Token(TokenType.EOL, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void extractQuery4() {
		Lexer lexer = new Lexer("firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

		Token correctData[] = { new Token(TokenType.WORD, "firstName"), new Token(TokenType.SPECIAL_CHARACTERS, ">"),
				new Token(TokenType.STRING_LITERAL, "A"), new Token(TokenType.WORD, "and"),
				new Token(TokenType.WORD, "firstName"),
				new Token(TokenType.SPECIAL_CHARACTERS, "<"),
				new Token(TokenType.STRING_LITERAL, "C"),
				new Token(TokenType.WORD, "and"),
				new Token(TokenType.WORD, "lastName"), 
				new Token(TokenType.WORD, "LIKE"), 
				new Token(TokenType.STRING_LITERAL, "B*ć"), 
				new Token(TokenType.WORD, "and"), 
				new Token(TokenType.WORD, "jmbag"), 
				new Token(TokenType.SPECIAL_CHARACTERS, ">"), 
				new Token(TokenType.STRING_LITERAL, "0000000002"), 
				new Token(TokenType.EOL, null) };

		checkTokenStream(lexer, correctData);
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

}
