package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * If parsing of some document is impossible
 * or if Lexer throws {@link LexerException},than
 * {@link SmartScriptParser} should throw this
 * exception.
 * @author Silvana Bakula
 *
 */

public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = -7146405271722626823L;
	/**
	 * This constructs new {@link SmartScriptParserException}.
	 */
	public SmartScriptParserException() {
		super();
	}
	/**
	 * This constructs new {@link SmartScriptParserException}
	 * with new exception's text message.
	 * @param string message
	 */
	public SmartScriptParserException(String string) {
		super(string);
	}

}
