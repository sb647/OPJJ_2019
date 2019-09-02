package hr.fer.zemris.java.custom.collections;
/**
 * This is interface that can be used to
 * check if some object is acceptable or not.
 * @author Silvana Bakula
 *
 */
public interface Tester {
	/**
	 * Returns true if the input argument had passed
	 * the test, otherwise false.
	 * @param obj object to be tested
	 * @return <code>true</code> or <code>false</code>
	 */
	abstract boolean test(Object obj);
}
