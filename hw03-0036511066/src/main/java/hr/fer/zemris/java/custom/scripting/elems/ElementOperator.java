package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This is a representation of some mathematical operators.
 * @author Silvana Bakula
 *
 */
public class ElementOperator extends Element {
	/**
	 * Symbol of a operator
	 */
	private String symbol;
	/**
	 * This constructs new {@link ElementOperator}.
	 * 
	 * @param symbol symbol of new operator
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	/**
	 * This returns {@code symbol}
	 */
	@Override
	public String asText() {
		
		return symbol;
	}
	
	@Override
	public String toString() {
		
		return symbol;
	}

}
