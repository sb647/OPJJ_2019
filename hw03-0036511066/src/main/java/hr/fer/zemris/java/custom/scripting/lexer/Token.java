package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;
/**
 * The Token class represents tokens 
 * returned by the {@link Lexer}.
 * @author Silvana Bakula
 *
 */
public class Token {
	/**
	 * Token type.
	 * @see TokenType
	 */
	TokenType type;
	/**
	 * Token value
	 */
	Object value;
	/**
	 * This constructs new Token with
	 * type and value.
	 * @throws NullPointerException if type is null
	 * @param type type of a new token
	 * @param value value of a new token
	 */
	public Token(TokenType type, Object value) {
		this.type=Objects.requireNonNull(type);
		this.value=value;
	}
	/**
	 * This is value getter.
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * This is token type getter.
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}
}
