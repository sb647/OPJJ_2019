package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * An enumeration of token types.
 * @author Silvana Bakula
 *
 */
public enum TokenType {
	    /**
	     * Document text,outside of tags.
	     */
		TEXT,
		/**
		 * Element in tag, starts by letter and after 
		 * follows zero or more letters, digits or underscores
		 */
		VARIABLE,
		/**
		 * Number in tag
		 */
		INTEGER,
		/**
		 * Number in tag,in digits-dot-digits format.
		 */
		DOUBLE,
		/**
		 * Element in tag,starts with '@', after which follows 
		 * a letter and after than can follow zero or more letters,
		 * digits or underscores
		 */
		FUNCTION,
		/**
		 * One single element in tag.
		 */
		SYMBOL,
		/**
		 * End of text.
		 */
		EOF, 
		/**
		 * Tag starts with this.
		 */
		TAG_OPEN,
		/**
		 * Tag ends with this.
		 */
		TAG_CLOSE,
		/**
		 * Token with value "for","end" or "=".
		 */
		KEY,
		/**
		 * Text between double quotes in tag.
		 */
		STRING;
}
