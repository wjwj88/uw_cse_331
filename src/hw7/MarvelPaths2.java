package hw7;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw6.MarvelPaths;
import hw6.MarvelParser;


public class MarvelPaths2{
	
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
		DiGraph<String, String> mygraph=MarvelPaths.graphCreation(filename);	
		DiGraph<String, Double> doubleGraph=new DiGraph();
		Map<String,Map<String,Integer>> data=new HashMap<String,Map<String,Integer>>();	
		for(String head:mygraph.NodeList()){
			doubleGraph.addNode(head);
			data.put(head, new HashMap<String,Integer>());
		}
		for(String head : mygraph.NodeList()){
			Set<EdgesOfNode<String,String>> s=mygraph.getEdgesOfNode(head);
			for(EdgesOfNode<String,String> e:s){
				if(!data.get(head).containsKey(e.getTail())){
					data.get(head).put(e.getTail(), 1);
				}else{
					data.get(head).put(e.getTail(), data.get(head).get(e.getTail())+1);
				}
			}
		}
		for(String head : data.keySet()){
			for(String tail:data.get(head).keySet() ){				
				doubleGraph.addEdge(head, tail, 1.0/data.get(head).get(tail));
			}
		}	
		return doubleGraph; 
	}	
	
	/**
     * find the least cost path from the node head to node tail (if any) from graph g
     * @param graph g where the path to be searched  
     * @param node head where the path starts 
     * @param node tail where the path stops
     * @requires g!=null&&head!=null&&tail!=null
     * @return the least cost path if any, otherwise null
     * @throws Exception if this specified file cannot be found, or 
     *         IllegalArgumentException if the filename is null
     */
	public static  ArrayList<EdgesOfNode<String,Double>> 
	                 searchMin(DiGraph<String,Double> g,String head,String tail) throws IllegalArgumentException{
		if(g.equals(null)){
			throw new IllegalArgumentException("the graph cannot be null");
		}
		if(head.equals(null)||tail.equals(null)){
			throw new IllegalArgumentException("the graph cannot be null");
		}
		if((!g.hasNode(head))||(!g.hasNode(tail))){
			throw new IllegalArgumentException("the node is not in the graph");
		}
		
		
		//priority queue to store the least cost path		
		PriorityQueue<ArrayList<EdgesOfNode<String,Double>>> activeQueue = 
				new PriorityQueue<ArrayList<EdgesOfNode<String,Double>>>(10, 
						new Comparator<ArrayList<EdgesOfNode<String,Double>>>() {
					// sort the path by the weight of last path and then by numbers of paths in the queue
							public int compare(ArrayList<EdgesOfNode<String,Double>> p1, 
											   ArrayList<EdgesOfNode<String,Double>> p2) {
								EdgesOfNode<String,Double> d1 = p1.get(p1.size() - 1);
								EdgesOfNode<String,Double> d2 = p2.get(p2.size() - 1);
								if (!(d1.getEdge().equals(d2.getEdge())))
									return d1.getEdge().compareTo(d2.getEdge());
								
								return p1.size() - p2.size();
							}
						});
		
		
		// set of nodes for which the minimum-cost is known 
		Set<String> knownSet=new HashSet<String>();
		// initialize the path from start to itself
		ArrayList<EdgesOfNode<String,Double>> initial=new ArrayList<EdgesOfNode<String,Double>>();
		initial.add(new EdgesOfNode<String,Double>(head,0.0));
		activeQueue.add(initial);
		
		
		//activeQueue contains all paths to unfinished nodes that only pass 
		//through finished nodes via the minimum cost paths to those nodes
		while(!activeQueue.isEmpty()){
			ArrayList<EdgesOfNode<String,Double>> minPath=activeQueue.poll();
			String minDest=minPath.get(minPath.size()-1).getTail();			
			//record current path cost 
			double oldCost=minPath.get(minPath.size()-1).getEdge();
			
			if(minDest.equals(tail)){
				return minPath;
			}			
			if(knownSet.contains(minDest)){
				continue;
			}			
			knownSet.add(minDest);
			
			
			//add all new paths to unfinished nodes going through  
			//the newly finished node via this shortest path
			for(EdgesOfNode<String,Double> e : g.getEdgesOfNode(minDest)){
				if(!knownSet.contains(e.getTail())){
					//update cost of the new path
					double newCost=oldCost+e.getEdge();
					ArrayList<EdgesOfNode<String,Double>> newPath=new ArrayList<EdgesOfNode<String,Double>>(minPath);
					newPath.add(new EdgesOfNode<String,Double>(e.getTail(),newCost));
					activeQueue.add(newPath);				
				}			
			}
		}
		
		//return null if no path found
		return null;		
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
		
		//create the graph
		DiGraph<String,Double> g=graphCreation("src/hw7/data/marvel.tsv");		
		System.out.println("This program is to find the shorest path"
				+ "(if any)between two nodes in Marvel graph");
		Scanner in=new Scanner(System.in);
		System.out.print("Please specify your starting node: ");
		String head=in.nextLine();
		System.out.print("Please specify your destination node: ");
		String tail=in.nextLine();
		System.out.println();
		
		
		//print out path
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
	        ArrayList<EdgesOfNode<String,Double>> path = MarvelPaths2.searchMin(g, head, tail);
	        
	        if(head.equals(tail)){
	        	result+="\ntotal cost: 0.000";
	        }else if (path==null||path.isEmpty()) {
	        	result += "\n" + "no path found";
	        }else{
	        	int i;
	    		for(i=0;i<path.size()-1;i++){
	    			result+="\n"+path.get(i).getTail()+" to "+path.get(i+1).getTail()+ 
	    					" with weight " + String.format("%.3f", path.get(i+1).getEdge()-path.get(i).getEdge()); 
	    		}
	    		result+="\n"+"total cost: "+String.format("%.3f", path.get(i).getEdge());
	    	}        
	        System.out.println(result);
        }
		in.close();
	}	
}