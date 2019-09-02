package hr.fer.zemris.java.custom.collections;
import java.lang.RuntimeException;
/**
 * If the stack is empty when
 * method pop or peek is called, the method 
 * should throw this exception.
 * @author Silvana Bakula
 *
 */

public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	/**
	 * This constructs new EmptyStackException.
	 */
	public EmptyStackException() {
		super("Stack is empty!");
	}
	
}
