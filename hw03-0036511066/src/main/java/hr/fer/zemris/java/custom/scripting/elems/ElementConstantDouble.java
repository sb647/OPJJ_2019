package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This is a representation of some constant of
 * a type double.
 * @author Silvana Bakula
 *
 */
public class ElementConstantDouble extends Element{
	/**
	 * Value of a constant.
	 */
	private double value;
	/**
	 * This constructs new {@link ElementConstantDouble} with
	 * this value.
	 * @param value value of new {@link ElementConstantDouble}
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * This returns value of a constant in string form.
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
