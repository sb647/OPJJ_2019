package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This is a representation of some function.
 * @author Silvana Bakula
 *
 */
public class ElementFunction extends Element{
	/**
	 * Name of the function
	 */
	private String name;
	/**
	 * This constructs new function
	 * with this name.
	 * @param name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		return '@'+ name;
	}
	/**
	 * This returns {@code name}.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	
}
