package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.demo.EvenIntegerTester;


/**
 * This class represents an implementation 
 * of resizable array-backed collection of objects. 
 * It contains various methods 
 * for manipulating arrays.
 * @author Silvana Bakula
 *
 */
public class ArrayIndexedCollection implements List {
	
	private int size;
	private Object[] elements;
	private long  modificationCount;
    
	private static final int DEFAULT_CAPACITY=16;
	
	/**
	 * Default constructor sets capacity
	 * to 16 and preallocates the elements 
	 * array of that size.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
		
	}
	/**
	 * This constructor sets capacity 
	 * to {@code initialCapacity} and
	 * preallocates the elements array of
	 * that size.
	 * @param initialCapacity capacity 
	 * @throws IllegalArgumentException if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {	
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements= new Object[initialCapacity];
		
	}
	/**
	 * This constructs new {@link ArrayIndexedCollection}
	 * with elements of other collection.
	 * Method invokes {@link addAll} to copy them into
	 * this newly constructed collection.
	 * @throws NullPointerException if other collection is null
	 * @param other other collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other,other.size());
	
	}

	/**
	 * This constructs new {@link ArrayIndexedCollection} with
	 * size of initialCapacity or size of other collection.
	 * If the initialCapacity is smaller than 
	 * the size of the given collection, the size of
	 * the given collection should be used for elements
	 * array preallocation.
	 * Method invokes {@link addAll} to copy elements into
	 * this newly constructed collection.
	 * 
	 * @param other
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection other,int initialCapacity) {
		
		if(other==null) {
			throw new NullPointerException();
		}
	
		elements= new Object[initialCapacity < other.size() ? other.size() : initialCapacity ];
		this.addAll(other);
		
	}
	
	@Override
	public int size() {	
		return this.size;  
	}
	/**
	 * Inserts the specified element into first
	 * empty space.
	 * If array is full,method reallocates it 
	 * by doubling its size.
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		
		if(this.size>=this.elements.length) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);          //modificationCount++?
		}
	
		this.elements[this.size]=Objects.requireNonNull(value);
		modificationCount++;
		this.size++;
		return;
		
	}
	/**
	 *For loop is used to loop through all elements
	 *to compare them with the value.
	 * @return <code>true</code> or <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		if(value==null) {
			return false;
		}
		for(Object e: elements) {
			if(value.equals(e)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns the object that is stored in
	 *  backing array at position index. 
	 * @param indeks of element
	 * @throws IndexOutOfBoundsException if index is 
	 * less than 0 or greater than (size of elements -1)
	 * @return element
	 */
	public Object get (int index) {
		if(index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}
	/**
	 * Allocates new array with size equals
	 *  to the size of this collections, 
	 *  fills it with collection content and
	 *	returns the array. 
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray=new Object[this.size];
		System.arraycopy(this.elements, 0, newArray, 0, this.size);
		return newArray;
	}
	
	
	/**
	 * Sets all elements to <code>null</code>.
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
		modificationCount++;
		this.size=0;
		
	}
	/**
	 * Inserts,does not overwrite, the given value 
	 * at the given position in array.
	 * @param value 
	 * @param position
	 * @throws IndexOutOfBoundsException if position is less than 0
	 * or greater than array size
	 * @throws NullPointerException if element is null
	 */
	public void insert(Object value,int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();	
		}
		if(value== null) {
			throw new NullPointerException();
		}
		if(this.size >= this.elements.length) {
			this.elements = Arrays.copyOf(this.elements, this.elements.length * 2);
		}
		
		Object elementToInsert=value;
		int i=position;
		while(elementToInsert!=null) {
			Object oldValue=elements[i];
			elements[i]=elementToInsert;
			elementToInsert=oldValue;
			i++;
		}
		modificationCount++;
	    this.size++;
		
	}
	
	/**
	 * Finds and returns the index of some specified element.
	 * If array does not contain that specified element,
	 * method returns -1.
	 * @param value 
	 * @throws NullPointerException if element is null
	 * @return index of element
	 */
	public int indexOf(Object value) {
		if(value==null) {
			return -1;
		}
		
		for(int i=0; i< this.size; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Removes element at specified index from collection. 
	 * Element that was previously at location index
	 * after this operation is on location index-1, etc. 
	 * @param index position of specified element
	 * @throws IndexOutOfBoundsException if position is less than 0
	 * or greater than size of array-1
	 */
	public void remove(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		for(int i=index+1; i< this.size; i++) {	
			this.elements[i-1]= this.elements[i];	
		}
			this.elements[this.size-1]=null;
			this.size--;
			modificationCount++;
			return;
		}
	/**
	 * Returns true only if the collection contains 
	 * given value as determined by equals method 
	 * and removes one occurrence of it.
	 */
	
	@Override	
	public boolean remove(Object object) {
		if(object==null) {
			return false;
		}
		if(this.contains(object)) {
			remove(indexOf(object));
			return true;
		}
		return false;
	}
	/**
	 * Implementation of {@link ElementsGetter} in
	 * {@link ArrayIndexedCollection}.
	 * @author Silvana
	 *
	 */
	private static class ArrayGetter implements ElementsGetter{
		/**
		 * Index of a current element.
		 */
		private int index=0;
		/**
		 * Reference to some collection.
		 */
		private ArrayIndexedCollection array;
		/**
		 * Current element.
		 */
		private Object pointer;
		/**
		 * Copy of modificationCount of the collection.
		 */
		private long savedModificationCount;
		/**
		 * This constructs new {@link ArrayGetter}
		 * with the collection.
		 * @param array {@link ArrayIndexedCollection} collection
		 */
		public ArrayGetter(ArrayIndexedCollection array) {
			this.array=array;
			pointer=array.elements[index];
			this.savedModificationCount=array.modificationCount;	
		}
		/**
		 * Returns true if the collection has more elements.
		 */
		public boolean hasNextElement() {
			if(savedModificationCount!= array.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return (pointer != null);
		}
		/**
		 * Returns the next element in the collection.
		 */
		public Object getNextElement() {
			if(savedModificationCount!= array.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if(! hasNextElement()) {
				throw new NoSuchElementException();
			}
			Object result= pointer;
			index++;
			try {
			pointer=array.elements[index];
			}
			catch (IndexOutOfBoundsException ex) {
			pointer=null;
			}
			return result;	
		}		
	}
	/**
	 * This creates new {@link ElementsGetter}.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayGetter(this);
	}
	
	
public static void main(String[] args) {
	Collection col1 = new LinkedListIndexedCollection();
	Collection col2 = new ArrayIndexedCollection();
	col1.add(2);
	col1.add(3);
	col1.add(4);
	col1.add(5);
	col1.add(6);
	col2.add(12);
	col2.addAllSatisfying(col1, new EvenIntegerTester());
	col2.forEach(System.out::println);
	}
}
