package hr.fer.zemris.java.custom.collections;
/**
 * Processor is model of an object capable 
 * of performing some operation on the passed object.
 * @author Silvana Bakula
 *
 */
public interface Processor {
	/**
	 * Performs operation on passed object.
	 * @param value passed object
	 */
	void process(Object value);

}
