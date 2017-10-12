package hw8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw7.MarvelPaths2;



/**
 * campusViewController is the view and controller of
 * the program and has the main method. This class allows user to 
 * type in some commands to call appropriate methods from view 
 * to get related information from the model.
 * <p>
 * 
 */
public class campusViewController {
	
	
	/**
	 * print all buildings' names (both abbreviated 
	 *              name and full name).
	 * 
	 * @param model model of the CampusRouteFindingTool
	 * @requires model != null
	 * @effect print all buildings' names (both abbreviated
	 *             name and full name)
	 */
	public static void printBuildings(CampusPathFinderModel model){
		Map<String,String> names=model.getShortName2LongName();
		Map<String,String> sortedNames=new TreeMap<String,String>(names);
		String result="Buildings:\n";
		
		// use TreeMap to sort the keys in lexicographic order
		for(String key : sortedNames.keySet()){
			result+="\t"+key+": "+sortedNames.get(key)+"\n";
		}
		System.out.println(result);		
	}
	
	
	/**
	 * print the menu
	 * 
	 * @effect print the menu
	 */
	public static void printMenu(){
		String result="Menu:\n";
		result+="\t"+"r to find a route\n";
		result+="\t"+"b to see a list of all buildings\n";
		result+="\t"+"q to quit\n";
		System.out.println(result);		
	}	
	
	
	/**
	 * print the shortest path from the starting node to 
	 * destination node on the campus graph
	 * 
	 * @param model model of the CampusRouteFindingTool
	 * @param start starting node of the path
	 * @param end destination node of the path
	 * @requires model != null && start !=null && end !=null 
	 * @effect print the shortest path from the starting node to
	 *             destination node on the campus graph
	 */
	@SuppressWarnings("unused")
	public static void printRoute(CampusPathFinderModel model,
			          String start, String end){
		Map<String,String> names=model.getShortName2LongName();
		
		if(model==null){
			throw new IllegalArgumentException("model cannot be null.");
		}
		if(start.equals(null)){
			throw new IllegalArgumentException("start node cannot be null.");
		}
		if(end.equals(null)){
			throw new IllegalArgumentException("destination node cannot be null.");
		}
		
		if((!names.containsKey(start))&&(!names.containsKey(end))){
			System.out.println("Unknown building: "+start);
			System.out.println("Unknown building: "+end+"\n");
		}else if(!names.containsKey(start)){
			System.out.println("Unknown building: "+start+"\n");	
		}else if(!names.containsKey(end)){
			System.out.println("Unknown building: "+end+"\n");	
		}else{
			Map<String, NodeCoordinate> shortName2Coordinate=
					   model.getShortName2Coordinate();
			String startCoor2String=""+shortName2Coordinate.get(start).getX()+
					  ","+shortName2Coordinate.get(start).getY();
			String endCoor2String=""+shortName2Coordinate.get(end).getX()+
					  ","+shortName2Coordinate.get(end).getY();
			
			
			String longName4Start=names.get(start);
			String longName4End=names.get(end);
			String result="Path from "+longName4Start+" to "+longName4End+":\n";			
			List<EdgesOfNode<String,Double>> path=
					model.findShortestPath(startCoor2String, endCoor2String);
			
			//get the required format for the shortest path
			int i;	
    		for(i=0;i<path.size()-1;i++){
    			NodeCoordinate startNode=model.getCoorOfNodeOnPath(path.get(i).getTail());
    			NodeCoordinate endNode=model.getCoorOfNodeOnPath(path.get(i+1).getTail());
    			String direction=startNode.getDirection(endNode);
    			String string4Node=" ("+String.format("%.0f",endNode.getX())+", "
    					+String.format("%.0f",endNode.getY())+")\n";
    			result+="\tWalk "+String.format("%.0f feet", 
    					path.get(i+1).getEdge()-path.get(i).getEdge())
    			        +" "+direction+" to"+string4Node;    			
    		}
    		result+="Total distance: "+String.format("%.0f feet\n", path.get(i).getEdge());
    		System.out.println(result);
		}		
	}
	

	/**
	 * Main method to allows user to print menu, building names 
	 * and find shortest walking route between two buildings on campus
	 * 
	 * @param args
	 */
    public static void main(String[] args) throws Exception {
	   
	   try{
		    String buildingFile="src/hw8/data/campus_buildings.dat";
		    String pathFile="src/hw8/data/campus_paths.dat";
		    CampusPathFinderModel model=new CampusPathFinderModel(buildingFile,pathFile);
		    printMenu();
		    
		    String option;
		    Scanner in=new Scanner(System.in);
		    System.out.print("Enter an option ('m' to see the menu): ");
		    while(true){
		    	option=in.nextLine();
		    	
		    	// echo empty input lines or lines beginning with # 
		    	if(option.length()==0||option.startsWith("#")){
			    	System.out.println(option);
			    	continue;
			    }
		    	
		    	if(option.equals("m")){
		    	    printMenu();
			    }else if(option.equals("b")){
			    	printBuildings(model);
			    }else if(option.equals("r")){
			    	System.out.print("Abbreviated name of starting building: ");
			    	String start=in.nextLine();
			    	System.out.print("Abbreviated name of ending building: ");
			    	String end=in.nextLine();
			    	printRoute(model, start, end);
			    }else if(option.equals("q")){
			    	break;
			    }else{
			    	System.out.println("Unknown option\n");
			    }
		    	System.out.print("Enter an option ('m' to see the menu): ");
		    	
		    }		   
	   }catch (Exception e) {
			System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	   }	    	   
	}  
    
}    
