package hr.fer.zemris.java.hw06.shell.commands.massrename;
/**
 * This class represents an implementation of
 * name builder expression lexer.
 * <p>
 * Lexer iterates over character array and builds
 * a token of a type String, until symbol '$' appears.
 * If '$' appears, next expected token is token of a
 * type GROUP. After (and if) '}' appears, lexer cheks 
 * if group is valid and throws
 * {@link NameBuilderLexerException} if not. 
 * 
 * @author Silvana
 *
 */
public class NameBuilderLexer {
	/**
	 * Array of separated characters.
	 */
	private char[] chars;
	/**
	 * Current index in {@code chars}.
	 */
	int index;
	/**
	 * Last extracted token.
	 */
	private Token token;
	/**
	 * If group is valid, it must match this expression.
	 */
	private static final String VALID_GROUP = "\\$\\{\\d+\\}";
	/**
	 * If extended group is valid, it must match this expression.
	 */
	private static final String VALID_EXTENDED_GROUP = "\\$\\{\\d+,\\d+\\}";
	/**
	 * Sole constructor.
	 * It constructs new {@link NameBuilderLexer} with given 
	 * expression.
	 * @param text given string 
	 */
	public NameBuilderLexer(String text) {
		chars = text.toCharArray();
	}
	/**
	 * This method returns last extracted token.
	 * @return {@code token}
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * This method invokes {@link extractNewToken}
	 * to extract new token and returns that token.
	 * @return  {@code token}
	 */
	public Token nextToken() {
		extractNewToken();
		return getToken();
	}
	/**
	 * This method extracts new token and sets {@code token}.
	 * 
	 * @throws NameBuilderLexerException if given 
	 * string has invalid symbols in group or if 
	 * group is not closed properly, or if someone 
	 * invokes {@link nextToken} after EOF is generated
	 */
	private void extractNewToken() {
		
		if(getToken() != null && getToken().getType()== TokenType.EOF) {
			throw new NameBuilderLexerException();
		}
		
		if(index >= chars.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		
		if(chars[index]== '$') {
			StringBuilder sb = new StringBuilder();
			sb.append('$');
			index++;
			while(index < chars.length && chars[index] != '}') {
				sb.append(chars[index++]);
			}
			if(index < chars.length && chars[index] == '}') {
				sb.append('}');
				index++;
			}
			
			sb.toString().replaceAll(" ", "");
			if(sb.toString().matches(VALID_GROUP) || sb.toString().matches(VALID_EXTENDED_GROUP)) {
				token = new Token(TokenType.GROUP, sb.toString());
				return;
			}
			throw new NameBuilderLexerException("Invalid group expression!");
		}
		
		
		StringBuilder sb = new StringBuilder();
		while(index < chars.length && chars[index] != '$') {
			sb.append(chars[index]);
			index++;
		}
		
		token = new Token(TokenType.STRING, sb.toString());
	}
		
}
