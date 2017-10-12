package hw6;// generic version-Jun Wang 5:00
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import hw5.DiGraph;
import hw5.EdgesOfNode;




public class MarvelPaths{
	
	/**
     * create a DiGraph from the filename
     * @param filename
     * @requires filename is not null and can be found by this code
     * @effects create a DiGraph
     * @return create a DiGraph
     * @throws Exception if this specified file cannot be found, or 
     *         IllegalArgumentException if the filename is null
     */
	public static DiGraph graphCreation(String filename) throws Exception{	
		DiGraph<String, String> mygraph=new DiGraph();
		HashSet<String> characters = new HashSet<String>();
		HashMap<String, List<String>> books=new HashMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books); 
		if(filename.equals(null)){
			throw new IllegalArgumentException("filename cannot be null");
		}
		
		//add nodes to the graph
		for(String s:characters){
			mygraph.addNode(s);
		}
		
		//add edges to graph
		for(String book:books.keySet()){
			for(String head:books.get(book)){
				for(String tail:books.get(book)){
					if(!head.equals(tail)){
						mygraph.addEdge(head, tail, book);
					}
				}
			}
		}
		return mygraph; 
	}	
	
	/**
     * find the shortest path from the node head to node tail (if any)from graph g
     * @param graph g, 
     * @param node head where the path starts 
     * @param node tail where the path stops
     * @requires g!=null&&head!=null&&tail!=null
     * @return the shortest path if any, otherwise null
     * @throws Exception if this specified file cannot be found, or 
     *         IllegalArgumentException if the filename is null
     */
	public static ArrayList<EdgesOfNode<String,String>> search(DiGraph g,String head,String tail) throws IllegalArgumentException{
		if(g.equals(null)){
			throw new IllegalArgumentException("the graph cannot be null");
		}
		if(head.equals(null)||tail.equals(null)){
			throw new IllegalArgumentException("the graph cannot be null");
		}
		if((!g.hasNode(head))||(!g.hasNode(tail))){
			throw new IllegalArgumentException("the node is not in the graph");
		}
				
		//nodes to visit
		LinkedList<String> nodesToVisit=new LinkedList<String>();
		
		// Each key in paths is a visited node and each value is 
		// a path from start to that node.
		HashMap<String, ArrayList<EdgesOfNode<String,String>>> pathList=
				new HashMap<String, ArrayList<EdgesOfNode<String,String>>>();
		pathList.put(head, new ArrayList<EdgesOfNode<String,String>>());
		nodesToVisit.add(head);

		while(!nodesToVisit.isEmpty()){
			String node=nodesToVisit.removeFirst();
			if(node.equals(tail)){
				ArrayList<EdgesOfNode<String,String>> pa=pathList.get(node);
				return new ArrayList<EdgesOfNode<String,String>>(pa);
			}
			
	    	ArrayList<EdgesOfNode<String,String>> edgeSet=new ArrayList<EdgesOfNode<String,String>>();									
			
	    	//sort all the edges to in increasing order of m's character name, with
	    	//edges to the same character visited in increasing order of comic book title
			edgeSet.addAll(g.getEdgesOfNode(node));
			Comparator<EdgesOfNode<String,String>> c=sortEdgeComparator();
			Collections.sort(edgeSet,c);
			
			for(EdgesOfNode<String,String> edge:edgeSet){
				String end=edge.getTail();
				
				//if this node has not been visited, then map the path to this node
				//by appending edge to the old path and replace the old path
				if(!(pathList.keySet().contains(end))){
					ArrayList<EdgesOfNode<String,String>> pa_old=pathList.get(node);
					ArrayList<EdgesOfNode<String,String>> pa_new=new ArrayList<EdgesOfNode<String,String>>(pa_old);
					pa_new.add(edge);
					pathList.put(end,pa_new);
					nodesToVisit.add(end);//mark this node as visted
				}
			}																	
		}
		return pathList.get(head);		
	}
    	
	/**
     * compare two EdgesOfNode 
     * @requires the two EdgesOfNodes to be compared cannot be null
     * @return negative integer, zero or positive integer if first 
     * EdgesOfNode is less, equal or greater than the second EdgesOfNode  
     */
	public static Comparator<EdgesOfNode<String,String>> sortEdgeComparator(){
    	Comparator<EdgesOfNode<String,String>> c=new Comparator<EdgesOfNode<String,String>>(){
    		public int compare(EdgesOfNode<String,String> e1, EdgesOfNode<String,String> e2){
				if(!(e1.getTail().equals(e2.getTail()))){
					return e1.getTail().compareTo(e2.getTail());
				}
				
				if(!(e1.getEdge().equals(e2.getEdge()))){
					return e1.getEdge().compareTo(e2.getEdge());
				}
				return 0;
			}
    	};
    	return c;
	}
	
	
	/**
     * ask the user for starting and destination node and find the
     * shortest path(if any) between them and print it out
     * 
     * @param args for the main method
     * @requires the specified file marvel.tsv is in the same folder as this java code
     * @effects print out the shortest path(if any) or a suitable message to the console
	 * @throws Exception if this specified file cannot be found
     */
	public static void main(String[] args) throws Exception {
		DiGraph g=graphCreation("src/hw6/data/marvel.tsv");
		System.out.println("This program is to find the shorest path"
				+ "(if any)between two nodes in Marvel graph");
		Scanner in=new Scanner(System.in);
		System.out.print("Please specify your starting node: ");
		String head=in.nextLine();
		System.out.print("Please specify your destination node: ");
		String tail=in.nextLine();
		System.out.println();
		
		if ((!g.hasNode(head)) && (!g.hasNode(tail))) {
			System.out.println("unknown character " + head);
			System.out.println("unknown character " + tail);
        } else if ((!g.hasNode(head))) {
        	System.out.println("unknown character " + head);
        } else if (!(g.hasNode(tail))) {
        	System.out.println("unknown character " + tail);
        } else {
	        String temp = head;
	        String result = "path from " + head + " to " + tail + ":";
	        ArrayList<EdgesOfNode<String,String>> path = MarvelPaths.search(g, head, tail);
	        
	        if(head.equals(tail)){
	        	result+="";
	        }else if (path.isEmpty()) {
	        	result += "\n" + "no path found";
	    	} else {
	    		for (EdgesOfNode<String,String> edge : path) {
	    			result += "\n" + temp + " to " + edge.getTail() + 
	    					" via " + edge.getEdge();
	    			temp = edge.getTail();
	    		}
	    	}	        
	        System.out.println(result);
        }
	}
}






















//public class MarvelPaths{
//		
//	/**
//     * create a DiGraph from the filename
//     * @param filename
//     * @requires filename is not null and can be found by this code
//     * @effects create a DiGraph
//     * @return create a DiGraph
//     * @throws Exception if this specified file cannot be found, or 
//     *         IllegalArgumentException if the filename is null
//     */
//	public static DiGraph graphCreation(String filename) throws Exception{	
//		DiGraph mygraph=new DiGraph();
//		HashSet<String> characters = new HashSet<String>();
//		HashMap<String, List<String>> books=new HashMap<String, List<String>>();
//		MarvelParser.parseData(filename, characters, books); 
//		if(filename.equals(null)){
//			throw new IllegalArgumentException("filename cannot be null");
//		}
//		
//		//add nodes to the graph
//		for(String s:characters){
//			mygraph.addNode(s);
//		}
//		
//		//add edges to graph
//		for(String book:books.keySet()){
//			for(String head:books.get(book)){
//				for(String tail:books.get(book)){
//					if(!head.equals(tail)){
//						mygraph.addEdge(head, tail, book);
//					}
//				}
//			}
//		}
//		return mygraph; 
//	}	
//	
//	/**
//     * find the shortest path from the node head to node tail (if any)from graph g
//     * @param graph g, 
//     * @param node head where the path starts 
//     * @param node tail where the path stops
//     * @requires g!=null&&head!=null&&tail!=null
//     * @return the shortest path if any, otherwise null
//     * @throws Exception if this specified file cannot be found, or 
//     *         IllegalArgumentException if the filename is null
//     */
//	public static ArrayList<EdgesOfNode> search(DiGraph g,String head,String tail) throws IllegalArgumentException{
//		if(g.equals(null)){
//			throw new IllegalArgumentException("the graph cannot be null");
//		}
//		if(head.equals(null)||tail.equals(null)){
//			throw new IllegalArgumentException("the graph cannot be null");
//		}
//		if((!g.hasNode(head))||(!g.hasNode(tail))){
//			throw new IllegalArgumentException("the node is not in the graph");
//		}
//				
//		//nodes to visit
//		LinkedList<String> nodesToVisit=new LinkedList<String>();
//		
//		// Each key in paths is a visited node and each value is 
//		// a path from start to that node.
//		HashMap<String, ArrayList<EdgesOfNode>> pathList=
//				new HashMap<String, ArrayList<EdgesOfNode>>();
//		pathList.put(head, new ArrayList<EdgesOfNode>());
//		nodesToVisit.add(head);
//
//		while(!nodesToVisit.isEmpty()){
//			String node=nodesToVisit.removeFirst();
//			if(node.equals(tail)){
//				ArrayList<EdgesOfNode> pa=pathList.get(node);
//				return new ArrayList<EdgesOfNode>(pa);
//			}
//			
//	    	ArrayList<EdgesOfNode> edgeSet=new ArrayList<EdgesOfNode>();									
//			
//	    	//sort all the edges to in increasing order of m's character name, with
//	    	//edges to the same character visited in increasing order of comic book title
//			edgeSet.addAll(g.getEdgesOfNode(node));
//			Comparator<EdgesOfNode> c=sortEdgeComparator();
//			Collections.sort(edgeSet,c);
//			
//			for(EdgesOfNode edge:edgeSet){
//				String end=edge.getTail();
//				
//				//if this node has not been visited, then map the path to this node
//				//by appending edge to the old path and replace the old path
//				if(!(pathList.keySet().contains(end))){
//					ArrayList<EdgesOfNode> pa_old=pathList.get(node);
//					ArrayList<EdgesOfNode> pa_new=new ArrayList<EdgesOfNode>(pa_old);
//					pa_new.add(edge);
//					pathList.put(end,pa_new);
//					nodesToVisit.add(end);//mark this node as visted
//				}
//			}																	
//		}
//		return pathList.get(head);		
//	}
//    	
//	/**
//     * compare two EdgesOfNode 
//     * @requires the two EdgesOfNodes to be compared cannot be null
//     * @return negative integer, zero or positive integer if first 
//     * EdgesOfNode is less, equal or greater than the second EdgesOfNode  
//     */
//	public static Comparator<EdgesOfNode> sortEdgeComparator(){
//    	Comparator<EdgesOfNode> c=new Comparator<EdgesOfNode>(){
//    		public int compare(EdgesOfNode e1, EdgesOfNode e2){
//				if(!(e1.getTail().equals(e2.getTail()))){
//					return e1.getTail().compareTo(e2.getTail());
//				}
//				
//				if(!(e1.getEdge().equals(e2.getEdge()))){
//					return e1.getEdge().compareTo(e2.getEdge());
//				}
//				return 0;
//			}
//    	};
//    	return c;
//	}
//	
//	
//	/**
//     * ask the user for starting and destination node and find the
//     * shortest path(if any) between them and print it out
//     * 
//     * @param args for the main method
//     * @requires the specified file marvel.tsv is in the same folder as this java code
//     * @effects print out the shortest path(if any) or a suitable message to the console
//	 * @throws Exception if this specified file cannot be found
//     */
//	public static void main(String[] args) throws Exception {
//		DiGraph g=graphCreation("src/hw6/data/marvel.tsv");
//		System.out.println("This program is to find the shorest path"
//				+ "(if any)between two nodes in Marvel graph");
//		Scanner in=new Scanner(System.in);
//		System.out.print("Please specify your starting node: ");
//		String head=in.nextLine();
//		System.out.print("Please specify your destination node: ");
//		String tail=in.nextLine();
//		System.out.println();
//		
//		if ((!g.hasNode(head)) && (!g.hasNode(tail))) {
//			System.out.println("unknown character " + head);
//			System.out.println("unknown character " + tail);
//        } else if ((!g.hasNode(head))) {
//        	System.out.println("unknown character " + head);
//        } else if (!(g.hasNode(tail))) {
//        	System.out.println("unknown character " + tail);
//        } else {
//	        String temp = head;
//	        String result = "path from " + head + " to " + tail + ":";
//	        ArrayList<EdgesOfNode> path = MarvelPaths.search(g, head, tail);
//	        
//	        if(head.equals(tail)){
//	        	result+="";
//	        }else if (path.isEmpty()) {
//	        	result += "\n" + "no path found";
//	    	} else {
//	    		for (EdgesOfNode edge : path) {
//	    			result += "\n" + temp + " to " + edge.getTail() + 
//	    					" via " + edge.getEdge();
//	    			temp = edge.getTail();
//	    		}
//	    	}	        
//	        System.out.println(result);
//        }
//	}
//}
