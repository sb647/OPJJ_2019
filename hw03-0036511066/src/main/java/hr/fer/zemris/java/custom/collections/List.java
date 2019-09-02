package hr.fer.zemris.java.custom.collections;

/**
 * The user of this interface has control 
 * over where in the List each element is inserted. 
 * The user can access elements by their integer index (position) 
 * or by their value,but can also remove them.
 * @author Silvana Bakula
 *
 */
public interface List extends Collection{
	/**
	 * Returns the object that is stored in backing 
	 * list at position index.
	 * @param index position of the element
	 * @return element
	 */
	Object get(int index);
	/**
	 * Inserts (does not overwrite) the given value 
	 * at the given position in list.
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position);
	/**
	 * Searches the collection and returns the index 
	 * of the first occurrence of the given value or -1 
	 * if the value is not found.
	 * @param value list element
	 * @return index
	 */
	int indexOf(Object value);
	/**
	 * Removes element at specified 
	 * index from collection.
	 * @param index specified position
	 */
	void remove(int index);

}
