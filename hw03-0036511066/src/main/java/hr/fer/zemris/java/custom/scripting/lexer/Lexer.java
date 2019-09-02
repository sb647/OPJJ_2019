package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Implementation of lexical analyzer.
 * @author Silvana Bakula
 *
 */

public class Lexer {
	/**
	 * Text converted to char array.
	 */
	private char[] data;
	/**
	 * Current token.
	 */
	private Token token; 
	/**
	 * Current index in char array.
	 */
	private int currentIndex; 
	/**
	 * Current lexer state.
	 * @see LexerState 
	 */
	private LexerState state;
	/**
	 * This constructs new Lexer.
	 * @throws NullPointerException is text is null
	 * @param text text to be tokenized
	 */
	public Lexer(String text) {
		data=Objects.requireNonNull(text).toCharArray();
		state=LexerState.TEXT;
	}
	/**
	 * This is token getter.
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * If current lexer state is {@link LexerState}.TEXT,
	 * this method invokes {@link extractFromText} method.
	 * Otherwise,it invokes {@link extractFromTag} method.
	 * @return current token
	 */
	public Token nextToken() {
		if(state==LexerState.TEXT) {
			extractFromText();
			return getToken();
		}
		else if(state==LexerState.TAG) {
			extractFromTag();
			return getToken();
		}
		return getToken();
	}
	/**
	 * This sets lexer state to state from argument.
	 * @throws NullPointerException if state is null
	 * @param state Lexer state
	 */
	public void changeState(LexerState state) {
		this.state=Objects.requireNonNull(state);
	}
	/**
	 * This extracts tokens from document text,until
	 * "{$" appears or {@code currentIndex} reaches
	 * end of data array.
	 * @throw {@link LexerException} if there is invalid escaping
	 */
	public void extractFromText() {
		if(getToken() != null && getToken().getType()== TokenType.EOF) {
			throw new LexerException();
		}
		
		StringBuilder sb= new StringBuilder();
		
		while(currentIndex < data.length ) {
			
			if(data[currentIndex]== '\\') {
				if(currentIndex==data.length-1) {
					break;
				}
				currentIndex++;
				switch(data[currentIndex]) {
					case '\\':
						sb.append(currentIndex);
						currentIndex++;
						break;
					case '{':
						sb.append('{');
						currentIndex++;
						break;
					default:
						throw new LexerException("Illegal String!");		
				}	
					continue;
			}
					
			if(currentIndex+1 < data.length && data[currentIndex]=='{' && data[currentIndex+1]=='$') {
				break;
			}
				
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		if(sb.length()==0 && currentIndex >= data.length ) {   //end of data array
			this.token=new Token(TokenType.EOF, null);
			return;
			
		}
		else if(sb.length()==0) {
			this.token= new Token(TokenType.TAG_OPEN,"{$");  //text starts with "{$"
			currentIndex+=2;
			return;
		}
		
		this.token=new Token(TokenType.TEXT,sb.toString());
		

	}
	/**
	 * This extracts tokens from tag until "$}" appears.
	 * @see TokenType
	 * @throws LexerException for invalid tokens or invalid escaping
	 * 
	 */
	public void extractFromTag() {
		if(getToken() != null && getToken().getType()== TokenType.EOF) {
			throw new LexerException();
		}
		
		if(this.currentIndex >= data.length) {                       
			token=new Token(TokenType.EOF, null);
			return;
		}
		if(currentIndex==0) {                                             //first chars are "{$"
			this.token=new Token(TokenType.TAG_OPEN, "{$");
			currentIndex+=2;
			return;
		}
	
		skipBlanks();
		
		if(data[currentIndex]== '$' && data[currentIndex+1]=='}') {	      // end of tag
			this.token=new Token(TokenType.TAG_CLOSE,"$}");
			currentIndex+=2;
			return;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			if(Character.toUpperCase(data[currentIndex]) =='F' && Character.toUpperCase(data[currentIndex+1]) == 'O' 
					&& Character.toUpperCase(data[currentIndex+2])=='R'){
						this.token = new Token(TokenType.KEY, "FOR");
						currentIndex+=3;
						return;
				
			}
			
			if(Character.toUpperCase(data[currentIndex]) =='E' && Character.toUpperCase(data[currentIndex+1]) == 'N' 
					&& Character.toUpperCase(data[currentIndex+2])=='D'){
						this.token = new Token(TokenType.KEY, "END");
						currentIndex+=3;
						return;
			}
			String string= checkIfValidVariable();
			this.token=new Token(TokenType.VARIABLE, string); //imat ce sigurno duljinu vecu ili jednaku 1,jer je prvi znak slovo
			return;
		}
		
		if(data[currentIndex]== '@' && Character.isLetter(data[currentIndex+1])) {
			currentIndex++;
			String string=checkIfValidVariable();
			this.token=new Token(TokenType.FUNCTION, string);
			return;
			
		}
		
		if(data[currentIndex]== '"') {
			currentIndex++;
			StringBuilder sb= new StringBuilder();
			while(currentIndex < data.length) {
				
				if(data[currentIndex]=='\\') {
					currentIndex++;
					switch(data[currentIndex]) {
						case '\\':
							sb.append('\\');
							currentIndex++;
							break;
						case '"':
							sb.append('\"');
							currentIndex++;
							break;
						case 'n':
							sb.append('\n');
							currentIndex++;
							break;
						case 'r':
							sb.append('\r');
							currentIndex++;
							break;
						case 't':
							sb.append('\t');
							currentIndex++;
							break;
						default:
							throw new LexerException();
					 }
						continue;
				}
				if(data[currentIndex]== '"') {
					currentIndex++;
					break;
				}
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			
			this.token=new Token(TokenType.STRING, sb.toString());
			return;
		}
		
		if(Character.isDigit(data[currentIndex])) {
			int startIndex= currentIndex;
			boolean dot=countDigits();
			int endIndex= currentIndex;
			
			try {
				if(dot==true) {
					Double doubleValue=Double.parseDouble(new String(data,startIndex,endIndex-startIndex));
					this.token=new Token(TokenType.DOUBLE ,doubleValue);
					return;	
				}
				
				Integer value=Integer.parseInt(new String(data,startIndex,endIndex-startIndex));
				this.token=new Token(dot == true ? TokenType.DOUBLE : TokenType.INTEGER , value);
				return;
			}
			catch(NumberFormatException e) {
				throw new LexerException("Ulaz ne valja!");
			}
		}
		
		if(data[currentIndex]== '=') {
			this.token=new Token(TokenType.KEY, "=");
			currentIndex++;
			return;
		}
		
		if(data[currentIndex]=='-') {
			if(!Character.isDigit(data[currentIndex+1])) {
				this.token=new Token(TokenType.SYMBOL,"-");
				currentIndex++;
				return;
				
			}
			
			int startIndex =currentIndex++;
			
			boolean dot=countDigits();
			int endIndex= currentIndex;
		
			try {
				if(dot==true) {
					Double doubleValue=Double.parseDouble(new String(data,startIndex,endIndex-startIndex));
					this.token=new Token(TokenType.DOUBLE ,-doubleValue);
					return;
					
				}
				Integer value=Integer.parseInt(new String(data,startIndex,endIndex-startIndex));
				this.token=new Token(dot == true ? TokenType.DOUBLE : TokenType.INTEGER,-value);
				return;
			}
			catch(NumberFormatException e) {
				throw new LexerException("Ulaz ne valja!");
			}	
		}
		
			this.token=new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
	}
	/**
	 * If tag contains blanks,ignore them.
	 */
	public void skipBlanks() {
		while(currentIndex<data.length) {
			 char c = data[currentIndex];
			 if(c==' ' || c=='\t' || c=='\r' || c=='\n') {
				 currentIndex++;
				 continue;
			 }
			 	break;
			 }
		
		 } 
	/**
	 * Variable name starts by letter and after 
	 * follows zero or more letters, digits or underscores.
	 * @return variable
	 */
	public String checkIfValidVariable() {
		StringBuilder sb= new StringBuilder();
		while(Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex]=='_') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		return sb.toString();
	}
	/**
	 * This increases currentIndex as long as
	 * element from data array on that position
	 * is digit.
	 * @return <code>true</code> if number is decimal,otherwise <code>false</code>
	 */
	public boolean countDigits() {
		boolean dot=false;
		while(currentIndex+2 < data.length && Character.isDigit(data[currentIndex])){
		try {	
			if(Character.isDigit(data[currentIndex])&& data[currentIndex+1]=='.' && Character.isDigit(data[currentIndex+2]) && dot==false){
				dot=true;
				currentIndex+=2;
				continue;
			}
		}
		catch(IndexOutOfBoundsException ex) {	
		}
			currentIndex++;
		}
		return dot;
	}
}