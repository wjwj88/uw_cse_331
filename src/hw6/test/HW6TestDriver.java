package hw6.test;// generic version-Jun Wang 5:00

import java.io.*;
import java.util.*;

import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW6TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
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
    private final Map<String, DiGraph> graphs = new HashMap<String,DiGraph>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
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
    	fileName = "src/hw6/data/" + fileName;
        graphs.put(graphName, MarvelPaths.graphCreation(fileName));
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
        DiGraph<String, String> g = graphs.get(graphName);
        
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
        String edgeLabel = arguments.get(3);
        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
    	
    	DiGraph<String,String> g=graphs.get(graphName);
    	g.addNode(parentName);
    	g.addNode(childName);
    	g.addEdge(parentName, childName, edgeLabel);
    	output.println("added edge "+edgeLabel+" from "+parentName+" to "+childName+" in "+graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {    	
    	DiGraph<String,String> g=graphs.get(graphName);
    	String result=graphName+" contains: ";
		for(String node:g.NodeList()){
//			String temp="\tnode "+node+" : "+g.adjacentyList(node).toString()+"\n";
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
    	
    	DiGraph<String,String> g=graphs.get(graphName);
    	String result="the children of "+parentName +" in "+graphName+" are:";
//		for(String children:g.adjacentyList(parentName)){
//			for(String edge:g.getEdge(parentName, children)){
//				String temp=children+"("+edge+") ";
//				result+=temp;
//			}
//		}
    	if(!g.hasNode(parentName)){
    		result+=" the node "+parentName+" is not in this graph";
    	}else{
//	    	Set<EdgesOfNode> ss=new HashSet<EdgesOfNode>(g.getEdgesOfNode(parentName));
	    	
	    	ArrayList<EdgesOfNode<String,String>> newss=new ArrayList<EdgesOfNode<String,String>>(g.getEdgesOfNode(parentName));
//	    	newss.addAll(g.getEdgesOfNode(parentName));
	    	Collections.sort(newss,MarvelPaths.sortEdgeComparator());
	    	for(EdgesOfNode<String,String> e:newss){
	    		result+=" "+e;
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
