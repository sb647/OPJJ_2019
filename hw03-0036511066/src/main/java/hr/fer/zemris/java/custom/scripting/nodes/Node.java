package hr.fer.zemris.java.custom.scripting.nodes;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
/**
 * This is base class for all graph nodes.
 * @author Silvana Bakula
 *
 */
public class Node { 
	/**
	 * Children nodes of this document node.
	 */
	ArrayIndexedCollection children;
	/**
	 * This adds new child node to children array.
	 * @throws NullPointerException if child is null
	 * @param child
	 */
	public void addChildNode(Node child) {
		if(this.children==null) {
			children=new ArrayIndexedCollection();
		}
			children.add(Objects.requireNonNull(child));
	}
	/**
	 * @throws NullPointerException if collection 
	 * of children does not exist
	 * @return number of children
	 */
	public int numberOfChildren() {
		try {
			return children.size();
		}
		catch(NullPointerException ex) {
			return 0;
		}
	}
	
	/**
	 * Returns Node at this index.
	 * @param index position of some child in collection
	 * @return child
	 */
	public Node getChild(int index) {
		return (Node)children.get(index);
	}

}
