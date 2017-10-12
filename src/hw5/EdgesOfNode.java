package hw5;//generic version-Jun Wang 5:00

/**
* <b>LabeledEdge</b> represents an outgoing edge with label of a node(head) and 
* the destination(tail) of the edge.
* <p>
* @specfield tail : String  // The destination of the edge.
* @specfield edge : String  // The label of the edge.
* 
* T:  the type of the nodes to be constructed 
* E:  the type of the labels associated with the nodes
*/



public class EdgesOfNode<T extends Comparable<T>, E extends Comparable<E>> 
                                implements Comparable<EdgesOfNode<T,E>> {

//public class EdgesOfNode<T extends Comparable<T>, E extends Comparable<E>> 
//                          implements Comparable<EdgesOfNode<T,E>> {
	// Rep invariant:
	// dest != null && label != null

	// Abstract function:
	// AF(this) = for all label(except this label) la in this graph labeled
	// edge, such that
	// la.destination != this.dest
	// la.label != this.label

	private final T tail; // destination of this edge
	private final E edge; // name of this edge

	/**
	 * Creates an edge.
	 * 
	 * @param tail destination of the edge
	 * @param edge name of the edge
	 * @requires tail != null && edge != null
	 * @effects constructs a labeled edge with destination tail and name edge
	 */
	public EdgesOfNode(T tail, E edge) {
		if (tail == null || edge == null)
			throw new IllegalArgumentException("Arguments cannot be null.");

		this.tail = tail;
		this.edge = edge;
		checkRep();
	}

	/**
	 * Returns the destination of this edge.
	 * 
	 * @return the destination of this edge
	 */
	public T getTail() {
		checkRep();
		return tail;
	}

	/**
	 * Returns the name of this edge.
	 * 
	 * @return the name of this edge
	 */
	public E getEdge() {
		checkRep();
		return edge;
	}

	/**
	 * Returns string representation of this edge.
	 * 
	 * @return string representation of this edge
	 */
	@Override
	public String toString() {
		checkRep();
		String result = tail.toString() + "(" + edge.toString() + ")";
		checkRep();
		return result;
	}

	/**
	 * Returns true if o represent same edge as this edge.
	 * 
	 * @param o
	 *            object to be compared
	 * @return true if o represents same edge (same tail and same edge name) as
	 *         this edge
	 */
	@Override
	public boolean equals(Object o) {
		checkRep();
		if (!(o instanceof EdgesOfNode))
			return false;

		EdgesOfNode l = (EdgesOfNode) o;
		checkRep();
		return tail.equals(l.tail) && edge.equals(l.edge);
	}

	/**
	 * Return hash code of this edge.
	 * 
	 * @return hash code of this edge
	 */
	@Override
	public int hashCode() {
		checkRep();
		return tail.hashCode() * edge.hashCode();
	}

	/**
	 * Compares this with the compared object for order. Returns a negative
	 * integer, zero, or a positive integer if this is less than, equal to, or
	 * greater than the compared object.
	 * 
	 * @param le
	 *            object to be compared
	 * @return a negative integer, zero, or a positive integer if this is less
	 *         than, equal to, or greater than compared object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(EdgesOfNode<T, E> e) {
		checkRep();

		// first compare tail
		if (!(tail.equals(e.tail))) {
			checkRep();
			return tail.compareTo(e.tail);
		}

		// compare edge
		if (!(edge.equals(e.edge))) {
			checkRep();
			return edge.compareTo(e.edge);
		}
		checkRep();
		return 0;
	}

	/**
	 * Check if representation invariant holds.
	 */
	private void checkRep() {
		assert(!tail.equals(null)) : "Null tail";
		assert(!edge.equals(null)) : "Null edge";
	}
}




















//public class EdgesOfNode implements Comparable<EdgesOfNode>{
//	// Rep invariant:
//	//     dest != null && label != null
//	
//	// Abstract function:
//	//     AF(this) = for all label(except this label) la in this graph labeled edge, such that
//	//                la.destination != this.dest
//	//                la.label != this.label
//	
//	private final String tail;   // destination of this edge
//	private final String edge;  // name of this edge
//	
//	/**
//	 * Creates an edge.
//	 * 
//	 * @param tail destination of the edge
//	 * @param edge name of the edge
//	 * @requires tail != null && edge != null
//	 * @effects constructs a labeled edge with 
//	 * destination <var>tail</var> and name <var>edge</var>
//	 */
//	public EdgesOfNode(String tail,String edge) {
//		if (tail == null || edge == null)
//			throw new IllegalArgumentException("Arguments cannot be null.");
//		
//		this.tail=tail;
//		this.edge=edge;
//		checkRep();
//	}
//	
//	/**
//	 * Returns the destination of this edge.
//	 * @return the destination of this edge
//	 */
//	public String getTail() {
//		checkRep();
//		return tail;
//	}
//	
//	/**
//	 * Returns the name of this edge.
//	 * 
//	 * @return the name of this edge
//	 */
//	public String getEdge() {
//		checkRep();
//		return edge;
//	}
//	
//	/**
//	 * Returns string representation of this edge.
//	 * @return string representation of this edge
//	 */
//	@Override
//	public String toString() {
//		checkRep();
//		String result = tail.toString() + "(" + edge.toString() + ")";
//		checkRep();
//		return result;
//	}
//	
//	/**
//	 * Returns true if o represent same edge as this edge.
//	 * @param o object to be compared
//	 * @return true if o represents same edge 
//	 * (same tail and same edge name) as this edge
//	 */
//	@Override
//	public boolean equals( Object o) {
//		checkRep();
//		if (!(o instanceof EdgesOfNode))
//			return false;
//		
//		EdgesOfNode l = (EdgesOfNode) o;
//		checkRep();
//		return tail.equals(l.tail) && edge.equals(l.edge);
//	}
//	
//	/**
//	 * Return hash code of this edge.
//	 * @return hash code of this edge
//	 */
//	@Override
//	public int hashCode() {
//		checkRep();
//		return tail.hashCode() * edge.hashCode();
//	}
//	
//	/**
//	 * Compares this with the compared object for order. Returns 
//	 * a negative integer, zero, or a positive integer if this  
//	 * is less than, equal to, or greater than the compared object.
//	 * 
//	 * @param le object to be compared
//	 * @return a negative integer, zero, or a positive integer if 
//	 * this is less than, equal to, or greater than compared object
//	 */
//	@Override
//	public int compareTo(EdgesOfNode e) {
//		checkRep();
//		
//		// first compare tail
//		if (!(tail.equals(e.tail))) {
//			checkRep();
//			return tail.compareTo(e.tail);
////			return tail.hashCode() - e.tail.hashCode();
//		}
//		
//		// compare edge 
//		if (!(edge.equals(e.edge))) {
//			checkRep();
//			return edge.compareTo(e.edge);
//		}				
//		checkRep();
//		return 0;
//	}
//	
//	/**
//	 * Check if representation invariant holds.
//	 */
//	private void checkRep()  {
//		assert (!tail.equals(null)): "Null tail";
//		assert (!edge.equals(null)): "Null edge";
//	}
//}
