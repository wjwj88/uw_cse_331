package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hw5.DiGraph;
import hw6.MarvelParser.MalformedDataException;


/**
 * Parser utility to load the building and path dataset.
 */
public class CampusParser {
	
		
	  /**
	   * Reads campus building dataset.
	   * 
	   * Each line of the input contains abbreviated name of a building, 
	   * full name of a building, and x and y coordinate of location of 
	   * the building's, separated by a tab character 
	   * 
	   * @param filename the file that will be read
	   * @param shortName2LongName a map of building's names 
	   *        that maps building's abbreviated name to full name;
	   *          typically empty when the routine is called
	   * @param shortName2Coordinate a map of building's coordinates that
	   *        maps building's abbreviated name to its coordinate; 
	   *        typically empty when the routine is called
	   * @requires filename is a valid file path
	   * @modifies shortName2LongName, shortName2Coordinate
	   * @effects fills shortName2LongName with a map from each  
	   *        abbreviated name to all full names
	   * @effects fills shortName2Coordinate with a map from each
	   *        abbreviated name to all coordinates
	   * @throws Exception if the file is not well-formed:
	   *          each line contains exactly four tokens separated by threes
	   *          tabs, or else starting with a # symbol to indicate 
	   *          a comment line.
	   */
	public static void parseBuildingData(String filename, Map<String,String> 
	   shortName2LongName, Map<String, NodeCoordinate> shortName2Coordinate) 
		    		                      throws Exception {
		
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new FileReader(filename));
		        String inputLine;
		        while ((inputLine = reader.readLine()) != null) {

		            // Ignore comment lines.
		            if (inputLine.startsWith("#")) {
		                continue;
		            }

		            // Parse the data, stripping out quotation marks and throwing
		            // an exception for malformed lines.
		            inputLine = inputLine.replace("\"", "");
		            String[] tokens = inputLine.split("\t");
		            if (tokens.length != 4) {
		                throw new MalformedDataException("Line should "
		                		+ "contain exactly three tabs: "
		                                                 + inputLine);
		            }

		            String shortName = tokens[0];
		            String longName = tokens[1];
		            double x = Double.parseDouble(tokens[2]);
		            double y = Double.parseDouble(tokens[3]);

		            // Add the parsed data to the shortName2LongName 
		            // and shortName2Coordinate collections.
		            shortName2LongName.put(shortName, longName);
		            shortName2Coordinate.put(shortName, new NodeCoordinate(x,y));
		        }
		    } catch (IOException e) {
		        System.err.println(e.toString());
		        e.printStackTrace(System.err);
		    } finally {
		        if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e) {
		                System.err.println(e.toString());
		                e.printStackTrace(System.err);
		            }
		        }
		    }
		  }
	
		
	
	/**
	   * Reads campus path dataset.
	   * 
	   * If the file is not empty, then it will start with
	   * a non-indented line with coordinates of a point(start node), 
	   * followed by indented lines, each of which contains coordinates 
	   * of another point(destination node) and the distance between 
	   * these two nodes. Non-indented line contains x coordinate of the 
	   * start node, followed by a comma, with y coordinate of the start 
	   * node. Indented line contains x coordinate of the destination node
	   * followed by a comma, with y coordinate of the destination node, then 
	   * followed by a colon with the distance between the start node and
	   * the destination node  
	   * 
	   * @param filename the file that will be read
	   * @param Coor2Node a map of path's nodes coordinate  
	   *        that maps path's node names(in the string format) to
	   *        coordinate;
	   *          typically empty when the routine is called
	   * @param campusPath a graph of path's nodes and it's 
	   *        associated edges and distance between nodes; 
	   *        typically empty when the routine is called
	   * @requires filename is a valid file path        
	   * @modifies Coor2Node, campusPath
	   * @effects fills Coor2Node with a map from each  
	   *        path node name to all node coordinates
	   * @effects fills campusPath with all nodes, edges and 
	   *          distance between nodes.        
	   * @throws Exception if the file is not well-formed:
	   *          each line should match the required format
	   *          or else starting with a # symbol to indicate 
	   *          a comment line.
	   */ 
	public static void parsePathData(String filename, Map<String, NodeCoordinate> 
	        Coor2Node, DiGraph<String, Double> campusPath) 
					throws Exception {
		
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new FileReader(filename));

		        String inputLine;
		        String head="",tail;
		        while ((inputLine = reader.readLine()) != null) {
		        	
		            // Ignore comment lines.
		            if (inputLine.startsWith("#")) {
		                continue;
		            }
		            		            
		            inputLine = inputLine.replace("\"", "");
	            	inputLine = inputLine.replace("\t", "");
	            	String[] tokens = inputLine.split(":");	            	
	            	
	            	String[] temp;

	            	// Add the parsed data to the Coor2Node 
		            // and campusPath collections.
		            if(tokens.length==1){
		            	head=tokens[0];
		            	if(!campusPath.hasNode(head)){
		            		campusPath.addNode(head);
		            	}
		            	if(!Coor2Node.containsKey(head)){
		            		temp=head.split(",");
		            		double x = Double.parseDouble(temp[0]);
		            		double y = Double.parseDouble(temp[1]);
		            		NodeCoordinate tempCoor=new NodeCoordinate(x,y);
		            		Coor2Node.put(head, tempCoor);		           		
		            	}
		            }else if(tokens.length==2){
		            	tail=tokens[0];
		            	double distance= Double.parseDouble(tokens[1]);
		            	if(!campusPath.hasNode(tail)){
		            		campusPath.addNode(tail);
		            	}			            
			            campusPath.addEdge(head, tail, distance);
			            if(!Coor2Node.containsKey(tail)){
		            		temp=tail.split(",");
		            		double x = Double.parseDouble(temp[0]);
		            		double y = Double.parseDouble(temp[1]);
		            		NodeCoordinate tempCoor=new NodeCoordinate(x,y);
		            		Coor2Node.put(tail, tempCoor);		           		
		            	}
		            }else{
		            	throw new Exception("File is not well-formed. "+
		            			inputLine);
		            }
		        }
		    } catch (IOException e) {
		        System.err.println(e.toString());
		        e.printStackTrace(System.err);
		    } finally {
		        if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e) {
		                System.err.println(e.toString());
		                e.printStackTrace(System.err);
		            }
		        }
		    }
		  }
}
