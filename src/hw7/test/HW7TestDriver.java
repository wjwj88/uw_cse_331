package hw7.test; //hw7 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw6.MarvelPaths;
import hw7.MarvelPaths2;
import hw7.test.HW7TestDriver;
import hw7.test.HW7TestDriver.CommandException;


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW7TestDriver td;

            if (args.length == 0) {
                td = new HW7TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW7TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }


    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, DiGraph<String,Double>> graphs = new HashMap<String,DiGraph<String,Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW7TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")){
            	loadGraph(arguments);
            } else if (command.equals("FindPath")){
            	findPath(arguments);
            }else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void loadGraph(List<String> arguments) throws Exception {
            if (arguments.size() != 2) {
                throw new CommandException("Bad arguments to loadGraph: " + arguments);
            }

            String graphName = arguments.get(0);
            String filename = arguments.get(1);
            
            loadGraph(graphName, filename);
    }    
        
        
    private void loadGraph(String graphName,String fileName) throws Exception {  
    	fileName = "src/hw7/data/" + fileName;
        graphs.put(graphName, MarvelPaths2.graphCreation(fileName));
        output.println("loaded graph " + graphName);
    }
    
    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String head = arguments.get(1).replace('_', ' ');
        String tail = arguments.get(2).replace('_', ' ');
        
        findPath(graphName, head, tail);
    }

    private void findPath(String graphName, String head, String tail) {
        DiGraph<String, Double> g = graphs.get(graphName);
        
        if ((!g.hasNode(head)) && (!g.hasNode(tail))) {
        	output.println("unknown character " + head);
        	output.println("unknown character " + tail);
        } else if ((!g.hasNode(head))) {
        	output.println("unknown character " + head);
        } else if (!(g.hasNode(tail))) {
        	output.println("unknown character " + tail);
        } else {
	        String temp = head;
	        String result = "path from " + head + " to " + tail + ":";
	        List<EdgesOfNode<String,Double>> path = MarvelPaths2.searchMin(g, head, tail);
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
	        output.println(result);
        }
    }        
        
    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {    	
    	graphs.put(graphName,new DiGraph());
    	output.println("created graph "+graphName);
    	
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {   	
    	DiGraph g=graphs.get(graphName);
    	g.addNode(nodeName);
    	output.println("added node "+nodeName+" to "+graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        
        double edgeLabel;
        try {
        	edgeLabel = Double.parseDouble(arguments.get(3));
        } catch(NumberFormatException nfe) {
        	throw new CommandException("Error! Last argument isn't a double number");
        }
        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            Double edgeLabel) {
    	
    	DiGraph<String,Double> g=graphs.get(graphName);
    	g.addNode(parentName);
    	g.addNode(childName);
    	g.addEdge(parentName, childName, edgeLabel);
    	output.println(String.format("added edge %.3f", edgeLabel) + " from " + 
                parentName +	" to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {    	
    	DiGraph<String,Double> g=graphs.get(graphName);
    	String result=graphName+" contains: ";
		for(String node:g.NodeList()){
			result+=node+" ";
		}
		output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
    	
    	DiGraph<String,Double> g=graphs.get(graphName);
    	String result="the children of "+parentName +" in "+graphName+" are:";

    	if(!g.hasNode(parentName)){
    		result+=" the node "+parentName+" is not in this graph";
    	}else{
	    	Set<EdgesOfNode<String,Double>> ss=new HashSet<EdgesOfNode<String,Double>>(g.getEdgesOfNode(parentName));
	    	for(EdgesOfNode<String,Double> e :ss){
	    		result += " " + e.getTail() + String.format("(%.3f)", e.getEdge());
	    	}
	    	
    	}
		output.println(result);   	    	
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
