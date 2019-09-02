package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This is a representation of some string value.
 * @author Silvana Bakula
 *
 */
public class ElementString extends Element{
	/**
	 * Value of {@link ElementString}
	 */
	private String value;
	/**
	 * This constructs new {@link ElementString}
	 * with this value.
	 * @param value value of new {@link ElementString}
	 */
	public ElementString(String value) {
		this.value = value;
	}
	/**
	 * This returns value of this element in text form.
	 */
	@Override
	public String asText() {
		return value;
	}
	
	@Override
	public String toString() {
		return "\"" +value + "\"";
	}
}
