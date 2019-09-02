package hr.fer.zemris.java.hw03.prob1;
/**
 * If text contains illegal escaping or if
 * user tries to extract next token,while current
 * token is of a type EOF,than lexer should throw
 * this exception.
 * @author Silvana Bakula
 *
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	/**
	 * This constructs new {@link LexerException}.
	 */
	public LexerException() {
		super("Lexer exception!");
	}
	/**
	 * This constructs new {@link LexerException}
	 * with new exception's text message.
	 * @param string message
	 */
	public LexerException(String string) {
		super(string);
	}
}
