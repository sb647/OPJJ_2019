package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;
/**
 * A node representing a piece of textual 
 * data. It inherits from Node class.
 * @author Silvana Bakula
 *
 */
public class TextNode extends Node{
	/**
	 * {@link TextNode} has property text. 
	 */
	private String text;
	/**
	 * This constructs new {@link TextNode} 
	 * with this text.
	 * @throws NullPointerException if text is null
	 * @param text piece of textual data
	 */
	public TextNode(String text) {
		this.text=Objects.requireNonNull(text);
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
