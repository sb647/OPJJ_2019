package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementing this interface allows an object
 * to have easily reachable elements. 
 * @author Silvana Bakula
 *
 */
public interface ElementsGetter {
	/**
	 * Returns true if the iteration has more elements.
	 * @return <code>true</code> or <code>false</code>
	 */
	boolean hasNextElement();
	/**
	 * If {@link hasNextElement()} returns true,
	 * this method returns the next element 
	 * in the iteration.
	 * @return next element
	 */
	Object getNextElement();
	/**
	 * Performs the given action for each remaining 
	 * element until all elements have been processed.
	 * @param processor specifies the operation to be 
	 * performed on passed elements
	 */
	default void processRemaining(Processor processor) {
		
		while(hasNextElement()) {
			Objects.requireNonNull(processor).process(getNextElement());
		}
	}
}
