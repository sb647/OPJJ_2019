package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This is a representation of some constant of
 * type Integer.
 * @author Silvana Bakula
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Value of this constant.
	 */
	private int value;
	
	/**
	 * This constructs new {@link ElementConstantInteger}
	 * with this value.
	 * @param value value of newly constructed constant
	 */
	public ElementConstantInteger(int value) {
		
		this.value = value;
	}

	/**
	 * This returns {@code value} in text form.
	 */
	@Override
	public String asText() {
		
		return String.valueOf(value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
