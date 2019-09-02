package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;
/**
 * This is representation of a variable.
 * @author Silvana Bakula
 *
 */
public class ElementVariable extends Element{
	/**
	 * Each {@link ElementVariable} has it's name.
	 */
	private String name;
	/**
	 * This constructs new {@link ElementVariable}
	 * @param name name of newly constructed variable
	 */
	
	public ElementVariable(String name) {
		this.name =Objects.requireNonNull(name);
	}
	/**
	 * This returns name of variable in text form.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		
		return name;
	}

}
