package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
/**
 *  A node representing a command which 
 *  generates some textual output dynamically. It inherits
 *  from Node class.
 *  @author Silvana Bakula
 *
 */
public class EchoNode extends Node{
	/**
	 * Elements of a echo node.
	 */
	private Element[] elements;
	/**
	 * This constructs new {@link EchoNode} with
	 * this elements.
	 * @param elements array of elements
	 */
	public EchoNode (Element[] elements) {
		this.elements=Objects.requireNonNull(elements);
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		for(Element e: elements) {
			sb.append(e.toString());
			sb.append(" ");
		}
		return sb.toString() ;
	}

}
