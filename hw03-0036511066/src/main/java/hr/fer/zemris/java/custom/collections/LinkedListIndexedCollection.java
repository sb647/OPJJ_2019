package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.demo.EvenIntegerTester;

/**
 * This class represents an implementation of
 *  linked list-backed collection of objects.
 *  
 * @author Silvana Bakula
 *
 */
public class LinkedListIndexedCollection implements List {
	
	private int size;
	private ListNode first;
	private ListNode last;
	private long  modificationCount;
	/**
	 * Default constructor sets nodes to <code>null</code>.
	 */
	public LinkedListIndexedCollection() {
		first=null;
		last=null;
	}
	/**
	 * This constructs new {@link LinkedListIndexedCollection} 
	 * with elements of other collection.
	 * Method invokes {@link addAll} to copy them into
	 * this newly constructed collection.
	 * @throws NullPointerException if other collection is null
	 * @param other other collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other== null) {
			throw new NullPointerException();
		}
		
		this.addAll(other);	
	}
	
	/**
	 * Returns the number of elements in this list.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * This converts linked list to array.
	 */
	@Override
	public Object[] toArray() {
		Object[] resultArray=new Object[this.size];
		ListNode node=first;
		int i=0;
		while(node!=null) {
			resultArray[i]=node.value;
			i++;
			node=node.next;
		}
		return resultArray;
	}
	/**
	 * Appends the specified element to the end of this list.
	 * If list is empty,sets first and last node to new node,and
	 * if not,sets last node to new node.
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		
		if(value==null) {
			throw new NullPointerException();
		}
		ListNode newNode=new ListNode(value);
		if(this.size==0) {
			first=newNode;
			last=newNode;
			size++;
			modificationCount++;
			return;
		}
		
		newNode.previous=last;
		newNode.next=null;
		last.next=newNode;

		this.last=newNode;
		modificationCount++;
		this.size++;
		
	}
	/**
	 * Returns the object that is stored in linked
	 * list at position index. 
	 * Method never has the complexity greater than n/2+1,
	 * because it can start searching from the first or 
	 * from the last node.
	 * @param index position of a specified element
	 * @return
	 */
	public Object get(int index) {
		if(index < 0 || index > (this.size - 1) ) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index < ((int)this.size / 2)){
			
			ListNode node=first;
			int i=0;
			while(i!= index) {
				node=node.next;
				i++;
			}
			return node.value;
		}
		
		int i=this.size-1;
		ListNode node=last;
		while(i!=index) {
			node=node.previous;
			--i;
		}
		return node.value;
	}
	/**
	 * Inserts (does not overwrite) the given value at the 
	 * given position in linked-list. Elements starting from
	 * this position are shifted one position
	 * @param value new element
	 * @param position index at which to insert the new element
	 * @throws NullPointerException if value is null
	 * @throws IndexOutOfBoundsException if position is less than 0
	 * or greater than array size
	 */
	public void insert(Object value,int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		if(value== null) {
			throw new NullPointerException();
		}
		ListNode newNode= new ListNode(value);
		
		if(position==this.size) {
			last.next=newNode;
			newNode.previous=last;
			last=newNode;
			modificationCount++;
			this.size++;
			
		}
		
		else if(position==0) {
			newNode.next=first;
			first.previous=newNode;
			first=newNode;
			modificationCount++;
			this.size++;
			
		}
		else {
			int i=0;
			ListNode node=first;
			while(i!= position) {
				i++;
				node=node.next;
			}
			
			newNode.previous=node.previous;
			node.previous.next=newNode;
			newNode.next=node;
			node.previous=newNode;
			modificationCount++;
			this.size++;
			
		}

	}
	/**
	 * Returns true only if the list contains 
	 * given value, as determined by equals method.
	 * @return <code>true</code> or <code>false</code>
	 */
	@Override
	public boolean contains(Object value) {
		if(value==null) {
			return false;
		}
		ListNode node=this.first;
		while(node!=null) {
			if(node.value.equals(value)) {
				return true;
			}
			node=node.next;
		}
		return false;
	}
	/**
	 * This sets first and last node to <code><code>null</code>
	 */
	@Override
	public void clear() {
		this.size=0;
		this.first=null;
		this.last=null;
		modificationCount++;
	}
	/**
	 * Searches the collection and returns 
	 * the index of the first occurrence of 
	 * the given value or -1 if the value is
     * not found.
	 * @param value
	 * @return
	 */
	public int indexOf(Object value) {
		if(value==null ) {
			return -1;
		}
		
		ListNode node= this.first;
		int index=0;
		while(node!= null) {
			if(node.value.equals(value)) {
				return index;
			}
				index++;
				node=node.next;
		}
		return -1;
	
	}
	/**
	 * Removes the element at the specified position
	 * in this list. Shifts any subsequent elements to 
	 * the left.
	 * @param index index of the element to remove
	 * @throws IndexOutOfBoundsException if position is less than 0
	 * or greater than array size -1
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if(index==0) {
			first.next.previous=null;
			first=first.next;

		}
		else if(index==this.size-1) {
			last.previous.next=null;
			last=last.previous;

		}
		else {
			int i=0;
			ListNode node= this.first;
			while(i!=index) {
				i++;
				node=node.next;	
			}
			node.previous.next=node.next;
			node.next.previous=node.previous;	
		
		}
		
		this.size--;
		modificationCount++;
		return;
		
	}
	
	/**
	 * Returns true only if the collection contains 
	 * given value as determined by equals method and removes
	 * one occurrence of it 
	 */
	@Override
	public boolean remove(Object object) {
		if(object==null) {
			return false;
		}
		
		if(contains(object)) {
			remove(indexOf(object));
			return true;
		}
		
		return false;
	}
	/**
	 * Implementation of list node.
	 *
	 */
	private static class ListNode{
		
		Object value;
		ListNode previous;
		ListNode next;
		
		
		public ListNode(Object value) {
			this.value=value;
			
		}
	}
	/**
	 * Implementation of {@link ElementsGetter} in
	 * {@link LinkedListIndexedCollection}.
	 * @author Silvana
	 *
	 */
	private static class ListGetter implements ElementsGetter{
		/**
		 * Reference to some {@link Collection#}.
		 */
		LinkedListIndexedCollection list;
		/**
		 * Current element.
		 */
		private ListNode pointer;
		/**
		 *Copy of modificationCount of the collection. 
		 */
		private long savedModificationCount;
		/**
		 * This constructs new {@link ListGetter} for
		 *  {@link LinkedListIndexedCollection}.
		 * @param list {@link LinkedListIndexedCollection} list
		 */
		public ListGetter(LinkedListIndexedCollection list) {
			this.list=list;
			this.pointer=list.first;
			this.savedModificationCount=list.modificationCount;
		}
			/**
			 * Returns true if the collection has more elements.
			 */
			public boolean hasNextElement() {
				if(savedModificationCount!= list.modificationCount) {
					throw new ConcurrentModificationException();
				}
				return (pointer != null);
			}
			/**
			 * Returns the next element in the collection.
			 */
			public Object getNextElement() {
				if(savedModificationCount!= list.modificationCount) {
					throw new ConcurrentModificationException();
				}
				if(! hasNextElement()) {
					throw new NoSuchElementException();
				}
					Object result= pointer.value;
					pointer=pointer.next;
					return result;	
			}
	
	 }
	/**
	 * This constructs new {@link ElementsGetter}.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ListGetter(this);
		
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