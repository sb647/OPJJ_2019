package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
/**
 * {@link ObjectStack} class represents the Adaptor in
 * Adapter pattern,while the Adaptee is {@link ArrayIndexedCollection}.
 * This pattern is used to modify interface of some already implemented
 * methods.
 * 
 * @author Silvana Bakula
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection array;
	/**
	 * This constructs new {@link ObjectStack}
	 */
	public ObjectStack() {
		array=new ArrayIndexedCollection();
	}
    /**
     * If array size is 0,return true.
     * @return <code>true</code> or <code>false</code>
     */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	/**
	 * @return size of array
	 */
	public int size() {
		
		return array.size();
	}

	/**
	 * Appends the specified element to the end
	 * of this array.
	 * @param value new element to be added
	 */
	public void push(Object value) {
		array.add(value);
		
	}
	/**
	 * Removes the elements from the last index
	 * @throws EmptyStackException if stack is empty
	 * @return element to be removed 
	 */
	public Object pop() {
		if(isEmpty()==true) {
			throw new EmptyStackException();
		}
			Object value= array.get(array.size()-1);
			array.remove(array.size()-1);
			return value;
	}

	/**
	 * Returns last element placed on stack 
	 * but does not delete it from stack. 
	 * @return last element on the stack
	 */
	public Object peek() {
		if(isEmpty()==true) {
			throw new EmptyStackException();
		}
		
		Object value= array.get(array.size()-1);
		return value;
	}

	/**
	 * Deletes all elements from stack/from array.
	 */
	public void clear() {
		array.clear();
		
	}
	
	
}
