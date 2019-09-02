package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw03.prob1.LexerException;

class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		assertEquals(TokenType.EOF , lexer.nextToken(). getType());
	}
	
	@Test
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");
		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testTextState() {
		String str="  Štefanija\r\n\t Automobil ";
		Lexer lexer=new Lexer(str);
		assertEquals(lexer.nextToken().getType(), TokenType.TEXT);
		assertEquals(lexer.nextToken().getType(), TokenType.EOF);	
	}
	
	@Test
	public void tokenTest() {
		String str= "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n";
		Lexer lexer=new Lexer(str);
		assertEquals(lexer.nextToken().getType(), TokenType.TEXT);
		assertEquals(lexer.nextToken().getType(), TokenType.TAG_OPEN);
		assertEquals(lexer.nextToken().getType(), TokenType.KEY);
		assertEquals(lexer.nextToken().getType(), TokenType.VARIABLE);
		assertEquals(lexer.getToken().getValue(), "i");
		assertEquals(lexer.nextToken().getType(), TokenType.INTEGER);
		assertEquals(lexer.nextToken().getType(), TokenType.INTEGER);
		assertEquals(lexer.nextToken().getType(), TokenType.INTEGER);
		assertEquals(lexer.getToken().getValue(), 1);
		assertEquals(lexer.nextToken().getType(), TokenType.TAG_CLOSE);
		assertEquals(lexer.nextToken().getType(), TokenType.TEXT);
		assertEquals(lexer.nextToken().getType(), TokenType.EOF);
			
	}
	
}
