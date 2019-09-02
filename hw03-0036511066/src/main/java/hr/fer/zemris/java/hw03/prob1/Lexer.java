package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;
/**
 * This is an implementation of lexical analyzer.
 * @author Silvana Bakula
 *
 */
public class Lexer {
	/**
	 * Text converted to char array.
	 */
	private char[] data; 
	/**
	 * Current token
	 */
	private Token token; 
	/**
	 * Current index in char array.
	 */
	private int currentIndex; 
	/**
	 * Current lexer state
	 */
	LexerState state;
	/**
	 * This constructs new {@link Lexer} with this text.
	 * Default state is basic lexer state.
	 * @throws NullPointerException if text is null
	 * @param text text to be processed
	 */
	public Lexer(String text) {
		if(text==null) {
			throw new NullPointerException();
		}
		
		this.data=text.toCharArray();
		currentIndex=0;
		state=LexerState.BASIC;
		
	}
	/**
	 * It is used to change current state 
	 * of lexer.
	 * @param state lexer state
	 */
	public void setState(LexerState state) {
		this.state=Objects.requireNonNull(state);
	}
	/**
	 * If current state is basic,this method invokes
	 * {@link extractNextToken} method and returns 
	 * token.
	 * Otherwise,it invokes {@extractNextTokenInExtended}.
	 * @return current token
	 */
	public Token nextToken() {
		if(state== LexerState.BASIC) {
			extractNextToken();
		}
		else {
			extractNextTokenInExtended();
		}
		return getToken();
	}
	/**
	 * Returns current token.
	 * @return
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * This method is used to reproduce new tokens.
	 * @see TokenType
	 */
	public void extractNextToken() {
		
		if(getToken() != null && getToken().getType()== TokenType.EOF) {
			throw new LexerException();
		}
		
		skipBlanks();
		
		if (currentIndex >= data.length) {
			this.token=new Token(TokenType.EOF, null);
			return;
		}
		if(Character.isLetter(data[currentIndex]) || data[currentIndex]== '\\') {
			StringBuilder sb=new StringBuilder();
			
			while(currentIndex < data.length ){
				if(Character.isLetter(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
					continue;
				}
				
				if(data[currentIndex]=='\\' ) {
					char c;
					try {
						c=data[currentIndex+1];
					}
					catch(IndexOutOfBoundsException ex) {
						throw new LexerException();
					}
					
					if(Character.isDigit(c) || c== '\\' ) {
						sb.append(c);
						currentIndex+=2;
						continue;		
				    }
					else {
						throw new LexerException();
					}	
				}
			
				else {
					break;
				}
			
			}
			this.token=new Token(TokenType.WORD, sb.toString());
			return;
		}
		
		if(Character.isDigit(data[currentIndex])) {
			int startIndex= currentIndex;
			
			while(currentIndex < data.length && Character.isDigit(data[currentIndex])){
				currentIndex++;
			}
			
			int endIndex= currentIndex;
			Long value;
			
			try {
				value=Long.parseLong(new String(data,startIndex,endIndex-startIndex));
				token=new Token(TokenType.NUMBER, value);
				return;
			}
			catch(NumberFormatException e) {
				throw new LexerException("Ulaz ne valja!");
			}
			
		}
		 
			Character c= data[currentIndex];
			currentIndex++;
			token=new Token(TokenType.SYMBOL, c);
			
	}
	
	
	/**
	 * 	This method is used to reproduce token,but in extended way.
	 *  Token/words are separated by blanks.
	 *  @throws LexerException 
	 */
	public void extractNextTokenInExtended() {
		if(getToken() != null && getToken().getType()== TokenType.EOF) {
			throw new LexerException();
		}
		
		skipBlanks();
		
		if (currentIndex >= data.length) {
			this.token=new Token(TokenType.EOF, null);
			return;
		}
		
		if(data[currentIndex] == '#') {
			token=new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return;
			
		}
		StringBuilder sb= new StringBuilder();
		while(currentIndex < data.length && !checkIfBlank(data[currentIndex]) && data[currentIndex] !='#' ) {
			sb.append(data[currentIndex]);	
			currentIndex++;
		}
		
		token=new Token(TokenType.WORD, sb.toString());	
	}
	/**
	 * If text contains blanks,ignore them.
	 */
	public void skipBlanks() {
	while(currentIndex<data.length) {
		 char c = data[currentIndex];
		 if(c==' ' || c=='\t' || c=='\r' || c=='\n') {
			 this.currentIndex++;
			 continue;
		 }
		 	break;
		 }
	 } 
	/**
	 * @param c some character
	 * @return <code>true</code> if c is blank,otherwise <code>false</code>
	 */
	public boolean checkIfBlank(char c) {
		 if(c==' ' || c=='\t' || c=='\r' || c=='\n') {
			 return true;
		 }
		 return false;
	}
			
}