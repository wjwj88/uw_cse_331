package hw8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw7.MarvelPaths2;


/**
 * CampusPathFinderModel represents the model of path 
 *      finding tool for the campus data  
 * <p>
 * 
 * @specfield shortName2LongName : Map<String, String>  
 * 			  // abbreviated building's name associated with its full name
 * @specfield shortName2Coordinate : Map<String, NodeCoordinate>
 * 			  // abbreviated building's name associated with its coordinate
 * @specfield pathCoor2Node : Map<String, NodeCoordinate>
 * 			  // node name of the path associated with its coordinate
 * @specfield graph : DGraph<String, Double>
 * 			  // a graph of all the possible paths with the associated
 *            // distance on campus 
 */
public class CampusPathFinderModel {
	
	
	
	// Rep invariant:
		//     shortName2LongName, shortName2Coordinate, 
	    //             pathCoor2Node and graph != null
		
	// Abstract function: 
	//     AF(this) = a CampusPathFinderModel m such that
	//		   m.building_names = this.shortName2LongName
	//		   m.building_coordinate = this.shortName2Coordinate
	//         m.pathNodeCoordinate = this.pathCoor2Node
	//		   m.graph = this.graph
	private Map<String,String> shortName2LongName;
	private Map<String, NodeCoordinate> shortName2Coordinate;
	private Map<String, NodeCoordinate> pathCoor2Node;
	private DiGraph<String, Double> graph;
		
	/**
	 * @param buildingFileName the building file that will be read
	 * @param pathFileName the path file that will be read 
	 * @requires buildingFileName not null and pathFileName not null
	 * @modifies shortName2LongName, shortName2Coordinate
	 *               pathCoor2Node, graph
	 * @effects fills shortName2LongName with a map from each 
	 *           abbreviated name to all full names
	 * @effects fills shortName2Coordinate with a map from each
	 *           abbreviated name to all coordinates
	 * @effects fills pathCoor2Node with a map from each 
	 *           path node name to all node coordinates
	 * @effects fills graph with all nodes, edges and 
	 *           distance between nodes.  
	 * @throws Exception if the file name is null                                                    
	 */
	public CampusPathFinderModel(String buildingFileName, String pathFileName)
	                                      throws Exception{
		if(buildingFileName==null){
			throw new IllegalArgumentException
			("building file name passed in cannot be null");
		}
		if(pathFileName==null){
			throw new IllegalArgumentException
			("path file name passed in cannot be null");
		}
		
		shortName2LongName = new HashMap<String,String>();
		shortName2Coordinate = new HashMap<String, NodeCoordinate>();
		pathCoor2Node = new HashMap<String, NodeCoordinate>();
		graph = new DiGraph<String, Double>();
		
		CampusParser.parseBuildingData(buildingFileName, shortName2LongName,
				           shortName2Coordinate);
		CampusParser.parsePathData(pathFileName, pathCoor2Node, graph);
		checkRep();
	}
						
	/**
	 * Returns a map that contains building's abbreviated 
	 *        name associated with its full name.
	 * 
	 * @return a map contains buildings abbreviated name 
	 *     associated with its full name
	 */
	public Map<String,String> getShortName2LongName(){
		checkRep();
		return new HashMap<String,String>(shortName2LongName); 
	}	
	
	
	/**
	 * Returns a map that contains building's abbreviated 
	 *        name associated with its coordinate.
	 * 
	 * @return a map contains buildings abbreviated name 
	 *     associated with its coordinate
	 */
	public Map<String, NodeCoordinate> getShortName2Coordinate(){
		checkRep();
		return new HashMap<String, NodeCoordinate>(shortName2Coordinate);
	}
	
	
	/**
	 * Returns a NodeCoordinate that corresponds to a node on the path.
	 * 
	 * @return a NodeCoordinate that corresponds to a node on the path 
	 * @throws Exception if the node is null     
	 */
	public NodeCoordinate getCoorOfNodeOnPath(String nodeName){
		checkRep();
		if(nodeName.equals(null)){
			throw new IllegalArgumentException
			("node name passed in cannot be null");
		}
		if(!pathCoor2Node.containsKey(nodeName)){
			throw new IllegalArgumentException
			("node name passed in is not in the graph");
		}
		return pathCoor2Node.get(nodeName);		
	}
	
//	public Map<String, NodeCoordinate> get(){
//		return new HashMap<String, NodeCoordinate>(pathCoor2Node);
//	}
	
	/**
	 * Returns a shortest path from two nodes on the graph.
	 * 
	 * @return a shortest path from two nodes on the graph      
	 */
	public List<EdgesOfNode<String,Double>> findShortestPath
	            (String head, String tail){
		checkRep();
		return MarvelPaths2.searchMin(graph, head, tail);
	}
	
	
	/**
	 * Check if representation invariant holds.
	 */
	private void checkRep(){
		assert (shortName2LongName!=null): 
			"shortName2LongName cannot be null";
		assert (shortName2Coordinate!=null): 
			"shortName2Coordinate cannot be null";
		assert (pathCoor2Node!=null): 
			"pathCoor2Node cannot be null";
		assert (graph!=null): 
			"graph cannot be null";
	}
 
}
