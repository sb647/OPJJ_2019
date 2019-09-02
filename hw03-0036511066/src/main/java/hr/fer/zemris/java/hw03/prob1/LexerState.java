package hr.fer.zemris.java.hw03.prob1;
/**
 * An enumeration of lexer states.
 * @author Silvana Bakula
 *
 */
public enum LexerState {
	/**
	 * Default state,until '#' appears.
	 */
	BASIC, 
	/**
	 * Lexer state until another '#' appears.
	 */
	EXTENDED;
}
