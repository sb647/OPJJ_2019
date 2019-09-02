package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * If given string has invalid symbols in group or 
 * if group is not closed properly, or if someone 
 * invokes {@link nextToken} after EOF is generated, 
 * {@link NameBuilderLexer} should throw this exception.
 * 
 * @author Silvana
 *
 */
public class NameBuilderLexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	/**
	 * This constructs new {@link NameBuilderLexerException}.
	 */
	public NameBuilderLexerException() {
		super();
	}
	/**
	 * This constructs new {@link NameBuilderLexerException}
	 * with given exception message.
	 * @param message given message
	 */
	public NameBuilderLexerException(String string) {
		super(string);
	}

}
