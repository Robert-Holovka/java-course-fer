package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void getReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void radAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void textWithWhitespaceOutside() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testTextOutside() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void singleBackslash() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void singleBackslashAtBegining() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\nešto aaaaa  ");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void singleBackslashInMid() {
		SmartScriptLexer lexer = new SmartScriptLexer("nešto\\ aaaaa  ");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void singleBackslashAtEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer("nešto aaaaa  \\");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void escapingBackslash() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\\\");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "\\"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingBackslashAtBegining() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\\\aaa ");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "\\aaa "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingBackslashInMid() {
		SmartScriptLexer lexer = new SmartScriptLexer(" aaa\\\\\\\\ aa ");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, " aaa\\\\ aa "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingBackslashAtEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer(" aaa aa \\\\");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, " aaa aa \\"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingSingleCurlyBracket() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\{");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "{"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingCurlyBracketAtBeginning() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\{ aaa ");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "{ aaa "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingCurlyBracketInMid() {
		SmartScriptLexer lexer = new SmartScriptLexer(" aa \\\\{ aa ");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, " aa \\{ aa "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingCurlyBracketAtEnd() {
		SmartScriptLexer lexer = new SmartScriptLexer(" aaa\\\\{ ");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, " aaa\\{ "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingComplexExample1() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT,
						"Example { bla } blu {$=1$}. Nothing interesting {=here}."),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };
		checkTokenStream(lexer, correctData);
	}

	@Test
	public void escapingComplexExample2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one ");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now actually write one "),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void beginTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void beginTagComplexTest() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now actually write one "),
				new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "=1$}") };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void beginTagComplexTest2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\\\{$=1$}. Now actually write one {$=1$}");

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "Example \\"),
				new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "=1$}. Now actually write one ") };

		checkTokenStream(lexer, correctData);
	}

	// 2. part, "TAG" mode

	@Test
	public void testNullTag() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}

	@Test
	public void testNotNullInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testEmptyInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNextInTag() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOFInExtended() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void echoTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("aaa \\\\{$=");

		SmartScriptToken[] correctData = { new SmartScriptToken(SmartScriptTokenType.TEXT, "aaa \\"),
				new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null) };
		checkTokenStream(lexer, correctData);
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken[] correct = { new SmartScriptToken(SmartScriptTokenType.SYMBOL, "="),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };
		
		checkTokenStream(lexer, correct);
	}
	
	@Test
	public void forLoopIntegers() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 10 $}");

		SmartScriptToken[] correctData = { new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null) };
		checkTokenStream(lexer, correctData);
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken[] correct = { new SmartScriptToken(SmartScriptTokenType.TEXT, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "i"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, -1),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, 10),
				new SmartScriptToken(SmartScriptTokenType.END_TAG, null) };
		checkTokenStream(lexer, correct);
	}

	@Test
	public void forLoopWithNegativeDecimal() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");

		SmartScriptToken[] correctData = { new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null) };
		checkTokenStream(lexer, correctData);
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken[] correct = { new SmartScriptToken(SmartScriptTokenType.TEXT, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "i"),
				new SmartScriptToken(SmartScriptTokenType.DECIMAL, -1.35),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "bbb"),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "\"1\""),
				new SmartScriptToken(SmartScriptTokenType.END_TAG, null) };
		checkTokenStream(lexer, correct);
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			assertEquals(expected.getType(), actual.getType());
			assertEquals(expected.getValue(), actual.getValue());
		}
	}
}
