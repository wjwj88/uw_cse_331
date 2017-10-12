package hw5;//generic version-Jun Wang 5:00
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hw6.MarvelPaths;
/**
 * <b>diGraph</B> is a mutable finite number of nodes and their associated edges.
 * <p>
 * Each diGraph contains some nodes {n1, n2, ..., n_k }, and some edges {e1, e2, ..., e_s} that 
 * connects these nodes. Note that node order and edge order may be different and some nodes may not have 
 * any edges connecting them. Nodes and their associated node and edges are stored accordingly using map.
 * <p>
 * Nodes {n1, n2, ..., n_k } are stored in the Map digraph as follows:
 *     digraph.put(n1,ArrayList_of_map for connection between another node and it's associated egde);
 *     .
 *     .
 *     .
 *     digraph.put(n_k,ArrayList_of_map for connection between another node and it's associated egde);
 * 
 * T:  the type of the nodes to be constructed 
 * E:  the type of the labels associated with the nodes
 *     
 * @author Jun Wang
 */


public class DiGraph<T extends Comparable<T>, E extends Comparable<E>> {
	
	/** Holds all the nodes and there associated connecting nodes and edges in this DiGraph */
	private final HashMap<T, Set<EdgesOfNode<T,E>>> digraph;//////////
	
	  // Abstraction Function:
	  // DiGraph, d, represents a graph containing all the nodes and the edges connecting 
	  // these nodes. These nodes and theirs associated nodes and edges are stores in a hashMap.
	  // Nodes {n1, n2, ..., n_k } are put in the map as the key in the same order as they are added 
	  // to the graph, and the key in the map would be the same as {n1, n2, ..., n_k }.
	  // Each key in the map would have an associated value, ArrayList<HashMap <String,String>>> arr to 
	  // store all the tail node and the associated edges connecting them. For example, node n_i and n_k
	  // is connected by the edge e_k, and node n_i and n_j is connected by the edge e_j, then the digraph
	  // will look like HashMap<n_i, ArrayList<HashMap<n_k,e_k>,HashMap<n_j,e_j>>>.

	  // Because ArrayList<HashMap <String,String>> is used to store the tail node and edges, 
	  // DiGraph, d allows multiple edges between the same nodes even with the same edge label.
	  // It also supports an edge connecting a node to itself.

	  // Representation Invariant for every DiGraph d:
	  // d != null && every node and edge cannot be null
	  // Any null node cannot be connected by a node or edge in the graph
	  // In other words, all nodes and edges at the end of each operation must be in this graph

	
	
    /**
     * @effects constructs an empty hashMap
     */
	public DiGraph(){
		digraph=new HashMap<T, Set<EdgesOfNode<T,E>>>();///////
//		checkRep();
	}
	
	
    /**
     * Add a node
     * @param vertex node to be added to the graph
     * @requires vertex is not in the graph and !vertex.equals(null)
     * @modifies this
     * @effects add the vertex to the graph
     * @return true if the node is added successfully, otherwise false
     */
	public boolean addNode(T vertex){
		
		boolean result=false;
		if((!digraph.containsKey(vertex))&&(!vertex.equals(null))){			
		    digraph.put(vertex, new HashSet<EdgesOfNode<T,E>>());////
		    result=true;
		}
//		checkRep();
		return result;
	}
	
    /**
     * Remove a node from the graph
     * @param node to be removed from the graph
     * @requires node is in the graph and !node.equals(null)
     * @modifies this
     * @effects remove the node from the graph
     * @return true if the node is removed successfully, otherwise false
     */
	public boolean removeNode(T node){
		boolean result=false;
		if(digraph.containsKey(node)&&(!node.equals(null))){				
		    digraph.remove(node);
		    result=true;
		    for(T head:digraph.keySet()){
		    	Set<EdgesOfNode<T,E>> temp=digraph.get(head);
		    	for(EdgesOfNode e:temp){
		    		if(e.getTail().equals(node)){
		    			digraph.get(head).remove(e);
		    		}
		    	}
		    }		    
		}
//		checkRep();
		return result;
	}
	
    /**
     * Add an edge connecting two nodes 
     * @param head,tail nodes to be connected by edge
     * @requires head and tail are in the graph and !edge.equals(null)
     * @modifies this
     * @effects add an edge connecting head and tail
     * @return true if the edge is added successfully, otherwise false
     */
	public boolean addEdge(T head, T tail, E edge) {
		boolean result=false;
		if((!head.equals(null))&&(!tail.equals(null))&&(!edge.equals(null))&&(hasNode(head))&&(hasNode(tail))){
			Set<EdgesOfNode<T,E>> oleEdges=digraph.get(head);//////
			EdgesOfNode<T,E> temp=new EdgesOfNode(tail,edge);
			if(!oleEdges.contains(temp)){
				digraph.get(head).add(temp);
				result=true;
			}
		}
//		checkRep();
		return result;		
	}
	
    /**
     * Remove an edge connecting two nodes 
     * @param head,tail nodes connected by edge
     * @requires head and tail are in the graph and they are connected by edge
     * @modifies this
     * @effects remove an edge connecting head and tail
     * @return true if the edge is removed successfully, otherwise false
     */
	public boolean removeEdge(T head, T tail, E edge){
		boolean result=false;
		if((!head.equals(null))&&(!tail.equals(null))&&(!edge.equals(null))&&(hasNode(head))&&(hasNode(tail))){
			if(isConnected(head,tail)){
				EdgesOfNode<T,E> e=new EdgesOfNode(tail,edge);
				if(digraph.get(head).contains(e)){
					digraph.get(head).remove(e);
					result=true;
				}
			}
		}
//		checkRep();
		return result;			
	}
	
	
    /**
     * Remove all edges connecting two nodes 
     * @param head,tail nodes connected by edges
     * @requires head and tail are in the graph and they are connected by edges
     * @modifies this
     * @effects remove all edges connecting head and tail
     * @return true if the edges are removed successfully, otherwise false
     */
	public boolean removeEdge(T head, T tail){
		boolean result=false;
		if((!head.equals(null))&&(!tail.equals(null))&&(hasNode(head))&&(hasNode(tail))){
			if(isConnected(head,tail)){
				for(EdgesOfNode<T,E> e:digraph.get(head)){
					if(e.getTail().equals(tail)){
						digraph.get(head).remove(e);
					}
				}
			}
			if(!isConnected(head,tail)){
				result=true;
			}
		}	
//		checkRep();
		return result;
		
	}	
	
    /**
     * Check if the graph has the node 
     * @param node to be checked 
     * @requires !node.equals(null)
     * @return true if the node is in the graph, otherwise false
     */
	public boolean hasNode(T node){
		return digraph.containsKey(node);
	}
	
	
    /**
     * Check if the graph has the edge 
     * @param edge to be checked 
     * @requires !edge.equals(null)&&head not null&&tail not null
     * @return true if the edge is in the graph, otherwise false
     */	
	public boolean hasEdge(T head, T tail, E edge){
		boolean result=false;
		if((!head.equals(null))&&(!tail.equals(null))&&(!edge.equals(null))&&(hasNode(head))&&(hasNode(tail))){
			EdgesOfNode<T,E> e=new EdgesOfNode(tail,edge);
			return digraph.get(head).contains(e);
		}
		return result;
	}
						
	
    /**
     * Get all nodes that head are outgoing to/children of head 
     * @param head from where edges are going out 
     * @requires head is in the graph and  not null
     * @return a set containing all the nodes outgoing from head
     */
	public Set<T> adjacentyList(T head){
		Set<T> list=new TreeSet<T>();
		for(T tail:NodeList()){
			if(isConnected(head,tail)){
				list.add(tail);
			}			
		}
		return list;		
	}
	
	
    /**
     * Get all nodes that tail are ingoing to/parent of tail 
     * @param tail where edges are coming into 
     * @requires tail is in the graph and  not null
     * @return a set containing all the nodes ingoing to tail
     */
	public Set<T> parentList(T tail){
		Set<T> list=new TreeSet<T>();
		for(T head:digraph.keySet()){
			if(isConnected(head,tail)){
				list.add(head);
			}
		}
		return list;
	}
			
	
    /**
     * Check if these two nodes are connected by edge(s)  
     * @param head and tail, nodes in this graph
     * @requires head and tail are in the graph and  both are not null
     * @return true if these two nodes are connected, otherwise false
     */
	public boolean isConnected(T head,T tail){
		boolean result=false;
		if((!head.equals(null))&&(!tail.equals(null))&&(hasNode(head))&&(hasNode(tail))){
			Set<EdgesOfNode<T,E>> temp=digraph.get(head);///////
			for(EdgesOfNode<T,E> e:temp){
				if(e.getTail().equals(tail)){
					result=true;
				}
			}			
		}
		return result;
	}
	
	
	/**
     * get the set of all the EdgesOfNode associated with head 
     * @param head to be search for in this graph
     * @requires head is not null
     * @return set of all the EdgesOfNode associated with head 
     */
	
	public Set<EdgesOfNode<T,E>> getEdgesOfNode(T head){
//		checkRep();		
		return new HashSet<EdgesOfNode<T,E>>(digraph.get(head));
	}
	
	
	/**
     * Get size of this hashMap
     * @requires the graph is not null
     * @return digraph.size()
     */
	public int size(){
		return digraph.size();
	}
	
	
	/**
     * Check if this graph is empty
     * @requires the graph is not null
     * @return digraph.isEmpty()
     */
	public boolean isEmpty(){
		return digraph.isEmpty();
	}
	
		
    /**
     * Get all edges outgoing from head 
     * @param head from where edges are going out 
     * @requires head is in the graph and  not null
     * @return an arrayList containing all the edges outgoing from head
     */
	public ArrayList<E> getEdge(T head){
//		checkRep();	
		ArrayList<E> list=new ArrayList<E>();
		if(hasNode(head)){
			Set <EdgesOfNode<T,E>> s=getEdgesOfNode(head);
			for(EdgesOfNode<T,E> e:s){
				list.add(e.getEdge());
			}
		}
		Collections.sort(list);
		return list;
	}
	
	
	/**
     * Get all edges in this graph
     * @requires graph is not null
     * @return an arrayList containing all the edges in this graph
     */
	public ArrayList<E> edgeList(){
		ArrayList<E> list=new ArrayList<E>();
		for(T head:digraph.keySet()){
			for(EdgesOfNode<T,E> e : digraph.get(head)){
				list.add(e.getEdge());
			}
		}
		Collections.sort(list);
		return list;
	}
		
	
    /**
     * Get all nodes in this graph
     * @requires the graph is not null
     * @return a set containing all the nodes in this graph
     */
	public Set<T> NodeList(){		
		return new TreeSet<T>(digraph.keySet());
	}			
					
	
    /**return a string representation of this DiGraph
     * @return A String representation of the expression represented by this,
     * with listing each node in this graph and followed by its associated 
     * children nodes that node can be connected to.
     */
	@Override
	public String toString(){
		String result="The graph can be described as follows:";
        result+="(The node is followed by it's children nodes)\n";		
		for(T node:NodeList()){
			String temp="\tnode "+node+" : "+adjacentyList(node).toString()+"\n";
			result+=temp;
		}
		return result;
	}
		
    
	/**
	 * Standard hashCode function.
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
	public int hashCode(){
		if(digraph.equals(null)){
			return 0;
		}
		return digraph.hashCode();
	}
	
	
	/**
	 * Standard equality operation.
	 *
	 * @param obj The object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a RatPoly and 'this'
	 *         and 'obj' represent the same DiGraph. 
	 */	
	@Override
	public boolean equals(Object obj){
		boolean result=true;
	    if (obj instanceof DiGraph) {
	    	DiGraph rp = (DiGraph) obj;	        	    	
	        if (this.digraph.size()!= rp.size()) {
	            result = false;
	        } else if((!this.digraph.keySet().containsAll(rp.digraph.keySet()))||
	        		(!rp.digraph.keySet().containsAll(this.digraph.keySet()))){
	        	result = false;
	        }else{
	        	for(T head:this.digraph.keySet()){
	        		Set <EdgesOfNode<T,E>> temp1=this.digraph.get(head);
	        		Set <EdgesOfNode<T,E>> temp2=(Set<EdgesOfNode<T, E>>)rp.digraph.get(head);
	        		if((!temp1.containsAll(temp2))||(!temp2.containsAll(temp1))){
	        			result=false;
	        		}
	        	}
	        }
	    }else{
	    	result = false;
	    }
	    return result;
	}
		
	
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert (!digraph.equals(null)): "Null digraph";
		for(T node:NodeList()){
			assert (!node.equals(null)): "Null node";
			assert (hasNode(node)): "node is not in this graph";
		}
	}

}

