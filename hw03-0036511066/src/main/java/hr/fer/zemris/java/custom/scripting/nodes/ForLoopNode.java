package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
/**
 * A node representing a single for-loop 
 * construct. It inherits from Node class.
 * @author Silvana Bakula
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * This constructs new {@link ForLoopNode}.
	 * @param variable property variable,of a type ElementVariable
	 * @param startExpression property startExpression of a type Element
	 * @param endExpression property startExpression of a type Element
	 * @param stepExpression property startExpression of a type Element
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		
		this.variable = Objects.requireNonNull(variable);
		this.startExpression =Objects.requireNonNull(startExpression);
		this.endExpression = Objects.requireNonNull(endExpression);
		this.stepExpression = stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append(this.variable.asText());
		sb.append(" ");
		sb.append(this.startExpression.toString());
		sb.append(" ");
		sb.append(this.endExpression.toString());
		sb.append(" ");
		sb.append(this.stepExpression != null ? this.stepExpression.toString() : "");
		
		
		return sb.toString();
	}
	

}
