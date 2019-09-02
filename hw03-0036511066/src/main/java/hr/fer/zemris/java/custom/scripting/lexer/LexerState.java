package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * An enumeration of lexer states.
 * @author Silvana Bakula
 *
 */
public enum LexerState {
	/**
	 * When in this state,lexer invokes 
	 * tokenization method to process text
	 * outside of tags.
	 */
	TEXT,
	/**
	 * When in this state,lexer invokes 
	 * tokenization method to process text
	 * inside of tags.
	 */
	TAG;
}
