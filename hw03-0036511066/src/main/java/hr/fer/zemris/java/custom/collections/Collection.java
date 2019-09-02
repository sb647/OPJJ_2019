package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * This interface represents some general
 * collection of objects.
 * @author Silvana Bakula
 *
 */
public interface Collection {

	/**
	 * If {@link size()} method returns 0,
	 * collection is empty,so this method 
	 * returns true.
	 * @return <code>true</code> or <code>false</code>
	 */
	public default boolean isEmpty() {
		return(this.size()==0 ? true : false);
	}
	/**
	 * Returns the number of currently stored
	 * objects in some collections.
	 * @return size of a collection
	 */
	int size();
	/**
	 * Adds the specified element to some
	 * collection.
	 * @param value value to be appended
	 */
	void add(Object value);
	/**
	 * Returns true if this collection contains the 
	 * specified element.
	 * @param value value of specified element
	 * @return <code>true</code> or <code>false</<code>
	 */
	boolean contains(Object value);
	/**
	 * Returns true only if the collection
	 *  contains given value as determined 
	 *  by equals method and removes one 
	 *  occurrence of it. 
	 * @param value of specified element
	 * @return <code>false</code>
	 */
	boolean remove(Object value);
	/**
	 * Allocates new array with size equals
	 * to the size of this collections, fills
	 * it with collection content and
	 * returns the array.
	 */
	 Object[] toArray();
	 /**
	  * Performs some operation on each
	  * element of the collection.
	  * @param processor specifies operation to be performed
	  * on passed objects
	  */

	default void forEach(Processor processor) {
		ElementsGetter getter= this.createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	/**
	 * Method adds into the current collection 
	 * all elements from the given collection. 
	 * This other collection remains unchanged.
	 * @throws NullPointerException if other collection is null
	 * @param other other collection
	 */
	public default void addAll(Collection other) {
		Objects.requireNonNull(other);
		
		class AddItem implements Processor{
			
			public void process(Object value) {
				add(value);
			}
		
		}
		other.forEach(new AddItem());
	}
	/**
	 * Removes all elements from collection.
	 */
	void clear();
	/**
	 * This creates new {@link ElementsGetter}.
	 */
	ElementsGetter createElementsGetter();
	/**
	 * This adds elements to the collection 
	 * from another collection,but only if test
	 * method from tester returns <code>true</code>.
	 * @param col other collection
	 * @param tester {@link Tester} used to test elements
	 */
	public default void addAllSatisfying(Collection col, Tester tester) {
		 ElementsGetter getter=col.createElementsGetter();
		 
		 while(getter.hasNextElement()) {
			 Object value=getter.getNextElement();
			 if(tester.test(value)) {
				 this.add(value);
			 }
		 }	
	}

}

