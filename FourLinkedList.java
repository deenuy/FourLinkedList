import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * Four-linked list implementation of the {@code List} interfaces. 
 * This implement a variation of a linked list and to investigate its time 
 * complexity for some of the list operations.
 * 
 * @author  	Deenanath G., David H., Parmveer R.
 * 
 * @param 		<E> the type of elements held in this Four-linked list type collection
 * @see     	List
 * @references 	Documentation is adopted from Oracle Java for best practices on Javadoc 
 * 
 */
public class FourLinkedList<E> implements List<E>{
	/** Nested Node Class **/
	private static class Node<E> {

		/**  Value for this node **/
		private E element;
		
	    /**  Pointer to previous node **/
		private Node<E> prev;
		
		/**  Pointer to next node **/
		private Node<E> next;
		
		/**  Pointer to second next node **/
		private Node<E> nextSecond;

		/**  Pointer to second previous node **/
		private Node<E> prevSecond;

	    /**
	     * Constructs a node containing the element of the specified and pointers of adjacent nodes
	     * 
	     * @param  element is the data added into node
	     */
	    public Node(Node<E> ps, Node<E> p, E e, Node<E> n, Node<E>ns) {
	    	this.element = e;
	    	this.prev = p;
	    	this.next = n;
	    	this.prevSecond = ps;
	    	this.nextSecond = ns;
	    }
	    
	    /** Constructs an empty node **/
	    public Node(E e) {
	    	this.element = e;
	    }
        
		/** Getter methods for Node Class **/
        public E getElement() {
            return element;
        }
        public Node<E> getNext() {
            return next;
        }
        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNextSecond() {
            return nextSecond;
        }
        public Node<E> getPrevSecond() {
            return prevSecond;
        }
        
        /** Setter methods for Node Class **/
        public void setNext(Node<E> n) {
            next = n;
        }
        public void setPrev(Node<E> p) {
            prev = p;
        }
        public void setNextSecond(Node<E> ns) {
        	nextSecond = ns;
        }
        public void setSecondPrev(Node<E> ps) {
        	prevSecond = ps;
        }
	}
	/** End of Nested Node Class **/
	
	
	/** Instance variables for the Four-LinkedList **/
	private Node<E> head = null; 	// head node of the list
	private Node<E> tail = null; 	// tail node of the list
	private int size = 0;			// number of nodes in the list
	private int debug = 0;
	
    /**
     * Constructs an empty four-linked list.
     */
	FourLinkedList() {
    	head = new Node<E>(null, null, null, null, null);
    	
    	tail = new Node<E>(null, head, null, null, null);
    	
    	head.setNext(tail);
		head.setNextSecond(null);
    	head.setPrev(null);
		head.setSecondPrev(null);
	}
	
	/** Getter and setter methods of FourLinkedList class **/
	
    /**
     * Appends the specified element to the end of this list.
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param e element to be appended to this list
     * @return {@code true} if an element is added to FourLinkedList list
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(E e) {
    	Node<E> newNode = new Node<E>(e);
    	
    	// check exception
    	if(e == null)
    		throw new NullPointerException("ERROR: Element is null");
    	
    	// case for addFirst element
    	if(size == 0) {
    		head = tail = newNode;
    		
    	} else if(size < 4){
    		
            tail.setNext(newNode);
            
    		newNode.setPrev(tail);
    		newNode.setSecondPrev(null);
    		newNode.setNextSecond(null);
    		newNode.setNext(null);
    		
            tail = newNode;
      
    	} else if((size != 0) && (size % 4 == 0)){
    		
            tail.setNext(newNode);
            
            newNode.setSecondPrev(head); // Pointed to Fourth element down from the node
    		newNode.setPrev(tail);
    		newNode.setNextSecond(null);
            newNode.setNext(null);
            
            tail = newNode;            
    	} else {
    		
            tail.setNext(newNode);
            
            newNode.setSecondPrev(tail.getPrev()); // Pointed to Fourth element down from the node
    		newNode.setPrev(tail);
    		newNode.setNextSecond(null);
            newNode.setNext(null);
            
            tail = newNode;
    	}
    		
    	size++; 	
    	return true;
    }
    
	/**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException
     */
	@Override
	public void add(int index, E element) {
		Node<E> walk = head;
		Node<E> newest = new Node<>(element);

		// check exception
        if(index < 0 || index >= size) {
        	throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        for(int i=0; i<index; i++) {
            if (walk.getNext() == null) {
                break;
            }
            walk = walk.getNext();
        }
        Node<E> currNode = walk;
        Node<E> predecessor = currNode.getPrev();
        Node<E> successor = currNode.getNext();
        
        if (index == 0) {
            successor.setNext(head);
            successor.setNextSecond(head.getNext());

            head.setPrev(newest);
            head.setSecondPrev(newest.getPrevSecond());

            head = newest;
        }
        
        currNode.setPrev(newest);
        currNode.setSecondPrev(currNode.getPrev());
        predecessor.setNext(newest);
        predecessor.setNextSecond(currNode);
        newest.setPrev(currNode.getPrev());
        newest.setSecondPrev(currNode.getPrevSecond());
        newest.setNext(currNode);
        newest.setNextSecond(currNode.getNext());

		size++;
	}
	
    /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException
     */
	@Override
	public E remove(int index) {
		int i = 0;
        Node<E> walk = head;
        E data = null;
        
        // check exception
        if(index < 0 || index > size) {
        	throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        
        if(size == 0) { //prevents removing from an empty linked list
            return null;
        }
        if (index == 0) { // remove from the begining
            head = head.getNext();//update next
            head.setPrev(null);  
        }
        else if (index == size) { // remove from the end
             Node<E> predecessor = tail.getPrev(); 
             
             predecessor.setNext(null);//update next
             tail = predecessor;   
        }
        else {
            while(i != index) { // remove from middle
                walk = walk.getNext();
                i++;
            }
         
            Node<E> predecessor = walk.getPrev();
            Node<E> successor = walk.getNext();
             
            predecessor.setNext(successor);//update next
            successor.setPrev(predecessor); // update prev
            walk.setNext(null);// cut off current node next
            walk.setPrev(null); // cut off current node prev
        }

        data = walk.getElement();

        size--;
        
        // now reset all the next next and next prev pointers 
        Node<E> current = head;
        int j =0;
        
        while(j != size) { // remove from middle
            if(j+4 < size) {
                current.setNextSecond(current.getNext().getNext().getNext().getNext());
            }
            if(j-4 >=0) {
                current.setSecondPrev(current.getPrev().getPrev().getPrev().getPrev());
            }
            current = current.getNext();
            j++;
        }
        
        return data;
	}
	   /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException
     */
	@Override
	public E get (int index) {
		Node<E> walk = head;
		E data = null;
		int i = 0;
        
		// check exception
        if(index < 0 || index >= size) {
        	throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        
		// Iterate through index to find position and get element		
		while(i < index+1 && walk != null) {
			
			data = walk.getElement();
			walk = walk.getNext();
					
			i++;
		}
		
		return data;
    }	
	
	/** Returns the number of elements in the list *
	 *
	 * @return the number of elements in this list
	 */
	public int size() {return size;}
	
	
	/**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
	@Override
	public void clear() {
		for(Node<E> n = head; n != null;) {
			Node<E> next = n.next;
		    n.element = null;
		    n.next = null;
		    n.prev = null;
		    n = next;
		}
	 
	    head = tail = null;
	    size = 0;
	}
	
	/** Returns a string representation of the object that "textually represents as Node LinkedList" *
	 *
	 * @return Returns a string representation of the object.
	 */
	public String toString() {
		Node<E> walk = head;
		
		// Return null on empty list
		if(size == 0) return null;
		
		StringBuilder sb = new StringBuilder("(");
		
		if(walk == tail)
			sb.append(head.getElement());
		
		while(walk != tail) {
			sb.append(walk.getElement());
			walk = walk.getNext();
			if(walk != tail)
				sb.append(", ");
		}
		if(walk == tail) {
			sb.append(", ");
			sb.append(walk.getElement());
		}
		sb.append(")");
		
		return sb.toString();
	}
	
    /**
     * Constructs an IndexOutOfBoundsException detail message.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}