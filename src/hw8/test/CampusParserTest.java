package hw8.test;

import hw8.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.DiGraph;
import hw5.test.CheckAsserts;

/**
 * This class contains a set of test cases that can be used to 
 * test the implementation of the CampusParser class.
 * <p>
 */
@SuppressWarnings("nullness")
public class CampusParserTest {
	
	private Map<String,String> shortName2LongName;
	private Map<String, NodeCoordinate> shortName2Coordinate;
	private Map<String, NodeCoordinate> Coor2Node;
	private DiGraph<String, Double> campusPath;
	
	@Before
	public void setUp(){	
		shortName2LongName = new HashMap<String, String>();
		shortName2Coordinate = new HashMap<String, NodeCoordinate>();
		Coor2Node = new HashMap<String, NodeCoordinate>();
		campusPath = new DiGraph<String, Double>();			
	}
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
	
	
	//////////////////////////////////////////////////////////////////////
	//// parseBuildingData Test: this is to test parsing empty building data
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseBuildingData() throws Exception {
		CampusParser.parseBuildingData("src/hw8/data/emptyBuilding.dat", 
				shortName2LongName, shortName2Coordinate);
		assertEquals(new HashMap<String, String>(), shortName2LongName);
		assertEquals(new HashMap<String, NodeCoordinate>(), shortName2Coordinate);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// parsePathData Test: this is to test parsing empty path data
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParsePathData() throws Exception {
		CampusParser.parsePathData("src/hw8/data/emptyPaths.dat", 
				Coor2Node, campusPath);
		assertEquals(new HashMap<String, NodeCoordinate>(), Coor2Node);
		assertEquals(new DiGraph<String, Double>(), campusPath);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// parseBuildingData Test: this is to test parsing two building data
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseTwoBuildingData() throws Exception {
		CampusParser.parseBuildingData("src/hw8/data/twoBuildings.dat", 
				shortName2LongName, shortName2Coordinate);
		Map<String, String> t1 = new HashMap<String, String>();
		Map<String, NodeCoordinate> t2 = new HashMap<String, NodeCoordinate>();
		t1.put("A",	"Building A");
		t1.put("B",	"Building B");
		t2.put("A",	new NodeCoordinate(3.0,0.0));
		t2.put("B",	new NodeCoordinate(0.0,4.0));
		assertEquals(t1, shortName2LongName);
		assertEquals(t2, shortName2Coordinate);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// parsePathData Test: this is to test parsing two path data
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseTwoPathData() throws Exception {
		CampusParser.parsePathData("src/hw8/data/twoPaths.dat", 
				Coor2Node, campusPath);
		Map<String, NodeCoordinate> map4Coor2Node = 
				new HashMap<String, NodeCoordinate>();
		DiGraph<String, Double> graph4campusPath=new DiGraph<String, Double>();		
		map4Coor2Node.put("3.0,0.0", new NodeCoordinate(3.0,0.0));
		map4Coor2Node.put("0.0,4.0", new NodeCoordinate(0.0,4.0));
		graph4campusPath.addNode("3.0,0.0");
		graph4campusPath.addNode("0.0,4.0");
		graph4campusPath.addEdge("3.0,0.0", "0.0,4.0", 5.0);
		graph4campusPath.addEdge("0.0,4.0", "3.0,0.0", 5.0);		
		assertEquals(map4Coor2Node, Coor2Node);
		assertEquals(graph4campusPath, campusPath);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// parseBuildingData Test: this is to test parsing 
	//// two building data with comments
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseTwoBuildingDataWithComments() throws Exception {
		CampusParser.parseBuildingData(
				"src/hw8/data/twoBuildingsWithComments.dat", 
				shortName2LongName, shortName2Coordinate);
		Map<String, String> t1 = new HashMap<String, String>();
		Map<String, NodeCoordinate> t2 = new HashMap<String, NodeCoordinate>();
		t1.put("A",	"Building A");
		t1.put("B",	"Building B");
		t2.put("A",	new NodeCoordinate(3.0,0.0));
		t2.put("B",	new NodeCoordinate(0.0,4.0));
		assertEquals(t1, shortName2LongName);
		assertEquals(t2, shortName2Coordinate);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// parsePathData Test: this is to test parsing  
	//// two path data with comments
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseTwoPathDataWithComments() throws Exception {
		CampusParser.parsePathData("src/hw8/data/twoPathsWithComments.dat", 
				Coor2Node, campusPath);
		Map<String, NodeCoordinate> map4Coor2Node = 
				new HashMap<String, NodeCoordinate>();
		DiGraph<String, Double> graph4campusPath=new DiGraph<String, Double>();		
		map4Coor2Node.put("3.0,0.0", new NodeCoordinate(3.0,0.0));
		map4Coor2Node.put("0.0,4.0", new NodeCoordinate(0.0,4.0));
		graph4campusPath.addNode("3.0,0.0");
		graph4campusPath.addNode("0.0,4.0");
		graph4campusPath.addEdge("3.0,0.0", "0.0,4.0", 5.0);
		graph4campusPath.addEdge("0.0,4.0", "3.0,0.0", 5.0);		
		assertEquals(map4Coor2Node, Coor2Node);
		assertEquals(graph4campusPath, campusPath);
	}
	
	//////////////////////////////////////////////////////////////////////
	//// parseBuildingData Test: this is to test parsing 
	//// six building data 
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseCityBuildingData() throws Exception {
		CampusParser.parseBuildingData(
				"src/hw8/data/cityBuildings.dat", 
				shortName2LongName, shortName2Coordinate);
		Map<String, String> map4shortName2LongName = 
				new HashMap<String, String>();
		Map<String, NodeCoordinate> map4shortName2Coordinate =
				new HashMap<String, NodeCoordinate>();
		map4shortName2LongName.put("A",	"Building A");
		map4shortName2LongName.put("B",	"Building B");
		map4shortName2LongName.put("C",	"Building C");
		map4shortName2LongName.put("D",	"Building D");
		map4shortName2LongName.put("E",	"Building E");
		map4shortName2LongName.put("F",	"Building F");
		map4shortName2Coordinate.put("A", new NodeCoordinate(3.0,0.0));
		map4shortName2Coordinate.put("B", new NodeCoordinate(0.0,4.0));
		map4shortName2Coordinate.put("C", new NodeCoordinate(4.0,7.0));
		map4shortName2Coordinate.put("D", new NodeCoordinate(7.0,3.0));
		map4shortName2Coordinate.put("E", new NodeCoordinate(10.0,10.0));
		map4shortName2Coordinate.put("F", new NodeCoordinate(15.0,15.0));
		assertEquals(map4shortName2LongName, shortName2LongName);
		assertEquals(map4shortName2Coordinate, shortName2Coordinate);
	}
	
	//////////////////////////////////////////////////////////////////////
	//// parsePathData Test: this is to test   
	//// parsing six path data 
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testParseCityBuildingPathData() throws Exception {
		CampusParser.parsePathData("src/hw8/data/citiBuildingPath.dat", 
				Coor2Node, campusPath);
		Map<String, NodeCoordinate> map4Coor2Node = 
				new HashMap<String, NodeCoordinate>();
		DiGraph<String, Double> graph4campusPath=new DiGraph<String, Double>();		
		map4Coor2Node.put("3.0,0.0", new NodeCoordinate(3.0,0.0));
		map4Coor2Node.put("0.0,4.0", new NodeCoordinate(0.0,4.0));
		map4Coor2Node.put("4.0,7.0", new NodeCoordinate(4.0,7.0));
		map4Coor2Node.put("7.0,3.0", new NodeCoordinate(7.0,3.0));
		map4Coor2Node.put("10.0,10.0", new NodeCoordinate(10.0,10.0));
		map4Coor2Node.put("15.0,15.0", new NodeCoordinate(15.0,15.0));
		graph4campusPath.addNode("3.0,0.0");
		graph4campusPath.addNode("0.0,4.0");
		graph4campusPath.addNode("4.0,7.0");
		graph4campusPath.addNode("7.0,3.0");
		graph4campusPath.addNode("10.0,10.0");
		graph4campusPath.addNode("15.0,15.0");
		
		graph4campusPath.addEdge("3.0,0.0", "0.0,4.0", 1.0);
		graph4campusPath.addEdge("3.0,0.0", "4.0,7.0", 4.0);
		graph4campusPath.addEdge("3.0,0.0", "7.0,3.0", 3.0);
		
		graph4campusPath.addEdge("0.0,4.0", "3.0,0.0", 1.0);	
		graph4campusPath.addEdge("0.0,4.0", "4.0,7.0", 2.0);	
		graph4campusPath.addEdge("0.0,4.0", "7.0,3.0", 3.0);	
		
		graph4campusPath.addEdge("4.0,7.0", "3.0,0.0", 4.0);
		graph4campusPath.addEdge("4.0,7.0", "0.0,4.0", 2.0);
		graph4campusPath.addEdge("4.0,7.0", "7.0,3.0", 3.0);
		
		graph4campusPath.addEdge("7.0,3.0", "3.0,0.0", 3.0);
		graph4campusPath.addEdge("7.0,3.0", "0.0,4.0", 3.0);
		graph4campusPath.addEdge("7.0,3.0", "4.0,7.0", 3.0);
		
		graph4campusPath.addEdge("10.0,10.0", "15.0,15.0", 5.0);
		graph4campusPath.addEdge("15.0,15.0", "10.0,10.0", 5.0);
		
		assertEquals(map4Coor2Node, Coor2Node);
		assertEquals(graph4campusPath, campusPath);
	}
}