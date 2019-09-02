package hr.fer.zemris.java.hw06.shell.commands.massrename;
/**
 * Token type enumeration. 
 * 
 * @author Silvana
 *
 */
public enum TokenType {
	/**
	 * Text outside of "&{...}".
	 */
	STRING,
	/**
	 * If string subsequence matches "&{...}",
	 * lexer extracts token of a type GROUP.
	 */
	
	GROUP,
	/**
	 * End of given string, no more tokens after 
	 * token of this type is generated.
	 */
	EOF;

}
