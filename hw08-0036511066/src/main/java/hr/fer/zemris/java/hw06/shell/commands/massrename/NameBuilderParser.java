package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;

 /**
 * This class represents an implementation of
 * name builder expression parser.
 * 
 * @author Silvana
 *
 */
public class NameBuilderParser {
	/**
	 * Instance of {@link NameBuilderLexer}.
	 */
	private NameBuilderLexer lexer;
	/**
	 * Parsed expression.
	 */
	private NameBuilder nameBuilder;
	/**
	 * This constructs new {@link NameBuilderParser}
	 * with given String expression.
	 * Constructor invokes {@link buildNameBuilder} method.
	 * 
	 * @param expression name builder expression to be parsed
	 * 
	 */
	public NameBuilderParser(String expression) {
		
		lexer = new NameBuilderLexer(expression);
		nameBuilder = buildNameBuilder();
		
	}
	/**
	 * This method consumes {@link NameBuilderLexer} extracted
	 * tokens and creates corresponding {@link NameBuilder} objects.
	 * 
	 * @return one object of a type {@link NameBuilder}
	 */
	private NameBuilder buildNameBuilder() {
		List<NameBuilder> list = new ArrayList<>();
		
		while(lexer.nextToken().getType() != TokenType.EOF) {
			switch(lexer.getToken().getType()) {
				case STRING:
					list.add(text(lexer.getToken().getValue().toString()));
					break;
					
				case GROUP:
					String value =lexer.getToken().getValue().toString();
					String[] parts = value.split(",");
					if(parts.length == 1) {
						list.add(group(Integer.parseInt(value.substring(value.indexOf('{')+1,value.indexOf('}')))));
						break;
					}
					
					int index = Integer.parseInt(parts[0].substring(value.indexOf('{')+1));
					char c = parts[1].startsWith("0") ? '0' : ' ';
					int width = Integer.parseInt(parts[1].substring(c == '0' ? 1 : 0 , parts[1].indexOf('}')));
					
					list.add(group(index, c, width));
					break;
					
				default:
					throw new IllegalArgumentException();
	
			}
		}
		return new NameBuilderImpl(list);
	}

	/**
	 * NameBuilder getter.
	 * @return {@code nameBuilder}
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}
	/**
	 * Creates and returns one object of a type
	 * {@link NameBuilder}. Method {@link execute}
	 * appends given string to {@link StringBuilder}.
	 * @param t given string
	 * @return new {@link NameBuilder} object
	 */
	private static NameBuilder text(String t) { 
		return ((r,sb) -> {sb.append(t);});
	}
	/**
	 * Creates and returns one object of a type
	 * {@link NameBuilder}. Method {@link execute}
	 * appends group with given index to {@link StringBuilder}.
	 * @param index index of group
	 * @return new {@link NameBuilder} object
	 */
	private static NameBuilder group(int index) { 
		return (r,sb) -> {
			try {
				sb.append(r.group(index)); 
			}catch(IndexOutOfBoundsException ex) {
				throw new IllegalArgumentException("There is no group "+index +"!");
			}
		};	
	}
	/**
	 * Creates and returns one object of a type
	 * {@link NameBuilder}. Method {@link execute}
	 * appends group with given index and fills 
	 * with paddings if needed.
	 * @param index index of group
	 * @param padding symbol,blank or zero
	 * @param minWidth min. width of string to be appended
	 * @return new {@link NameBuilder} object
	 */
	private static NameBuilder group(int index, char padding, int minWidth) { 
		return (r,sb) -> {
			try {
				int len = minWidth - r.group(index).toString().length();
				String pad = String.valueOf(padding);
				String str = new String(pad.repeat(len <= 0 ? 0: len) + r.group(index).toString());
				sb.append(str);
			}catch(IndexOutOfBoundsException ex) {
				throw new IllegalArgumentException("There is no group "+index +"!");
			}
			
		}; 
	}

}
