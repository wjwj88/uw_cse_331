package hw8.test;

import hw8.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.DiGraph;
import hw5.EdgesOfNode;
import hw5.test.CheckAsserts;


/**
 * This class contains a set of test cases that can be used to 
 * test the implementation of the CampusPathFinderModel class.
 * <p>
 */
@SuppressWarnings("nullness")
public class CampusPathFinderModelTest {
	
	// initialize file path and construct CampusPathFinderModel model
	private CampusPathFinderModel m1,m2,m3,m4,m5;
	String file1="src/hw8/data/emptyBuilding.dat";
	String file2="src/hw8/data/emptyPaths.dat";
	String file3="src/hw8/data/twoBuildings.dat";
	String file4="src/hw8/data/twoPaths.dat";
	String file5="src/hw8/data/twoBuildingsWithComments.dat";
	String file6="src/hw8/data/twoPathsWithComments.dat";
	String file7="src/hw8/data/cityBuildings.dat";
	String file8="src/hw8/data/citiBuildingPath.dat";
	String file9="src/hw8/data/campus_buildings.dat";
	String file10="src/hw8/data/campus_paths.dat";

	
	@Before
	public void setUp() throws Exception{
		m1 = new CampusPathFinderModel(file1, file2);
		m2 = new CampusPathFinderModel(file3, file4);
		m3 = new CampusPathFinderModel(file5, file6);
		m4 = new CampusPathFinderModel(file7, file8);
		m5 = new CampusPathFinderModel(file9, file10);
	}
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}			
			
	
	
    //////////////////////////////////////////////////////////////////////
    //// throw exception Test: this is to test throwing
    //// exception when passing a null file name
    //////////////////////////////////////////////////////////////////////
	@Test(expected = IllegalArgumentException.class)
	public void testConstructModelWithBuildingFileNull() throws Exception {
		new CampusPathFinderModel(null, file1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructModelWithPathFileNull() throws Exception {
		new CampusPathFinderModel(file2, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructModelWithBuildingAndPathFileNull() throws Exception {
		new CampusPathFinderModel(null, null);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////
	//// getShortName2Coordinate() Test: this is to test getting
	//// the Map of mapping short name to long name
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetShortName2LongName() {		
		assertEquals("{}", m1.getShortName2LongName().toString());
		assertEquals("{A=Building A, B=Building B}", 
				m2.getShortName2LongName().toString());
		assertEquals("{A=Building A, B=Building B}", 
				m3.getShortName2LongName().toString());
		assertEquals("{A=Building A, B=Building B, C=Building C, "
				+ "D=Building D, E=Building E, F=Building F}",
				m4.getShortName2LongName().toString());		
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// getShortName2Coordinate() Test: this is to test getting
	//// the Map of mapping short name to the associated coordinates
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetShortName2Coordinate() {	
		Map<String, NodeCoordinate> test =
				new HashMap<String, NodeCoordinate>();		
		assertEquals("{}", m1.getShortName2Coordinate().toString());
		test.put("A", new NodeCoordinate(0.0,0.0));
		test.put("B", new NodeCoordinate(3.0,4.0));	
		assertEquals("{A=(3.0,0.0), B=(0.0,4.0)}", 
				m2.getShortName2Coordinate().toString());		
		assertEquals("{A=(3.0,0.0), B=(0.0,4.0)}", 
				m3.getShortName2Coordinate().toString());
		test.put("C", new NodeCoordinate(4.0,7.0));
		test.put("D", new NodeCoordinate(7.0,3.0));
		test.put("E", new NodeCoordinate(10.0,10.0));
		test.put("F", new NodeCoordinate(15.0,15.0));
		assertEquals("{A=(3.0,0.0), B=(0.0,4.0), "
				+ "C=(4.0,7.0), D=(7.0,3.0), "
				+ "E=(10.0,10.0), F=(15.0,15.0)}", 
				m4.getShortName2Coordinate().toString());	
	}
	
	//////////////////////////////////////////////////////////////////////
	//// getCoorOfNodeOnPath() Test: this is to test getting
	//// the coordinate of a node from the path file data
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetCoorOfNodeOnPath() {
		assertEquals(new NodeCoordinate(3.0,0.0), 
				m2.getCoorOfNodeOnPath("3.0,0.0"));
		assertEquals(new NodeCoordinate(3.0,0.0), 
				m3.getCoorOfNodeOnPath("3.0,0.0"));
		assertEquals(new NodeCoordinate(0.0,4.0), 
				m2.getCoorOfNodeOnPath("0.0,4.0"));
		assertEquals(new NodeCoordinate(0.0,4.0), 
				m3.getCoorOfNodeOnPath("0.0,4.0"));
		assertEquals(new NodeCoordinate(4.0,7.0), 
				m4.getCoorOfNodeOnPath("4.0,7.0"));
		assertEquals(new NodeCoordinate(10.0,10.0), 
				m4.getCoorOfNodeOnPath("10.0,10.0"));	
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// findShortestPath() Test: this is to test finding
	//// the shortest path between different nodes
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testFindShortestPath() {
		// node A coordinate: 3.0,0.0
		// node C coordinate: 4.0,7.0
		// node B coordinate: 0.0,4.0
		// node D coordinate: 7.0,3.0
		String a_Coordinate="3.0,0.0";
		String c_Coordinate="4.0,7.0";
		String b_Coordinate="0.0,4.0";
		String d_Coordinate="7.0,3.0";
		
		assertEquals("[3.0,0.0(0.0), 0.0,4.0(1.0), 4.0,7.0(3.0)]",
				m4.findShortestPath(a_Coordinate, c_Coordinate).toString());
		assertEquals("[7.0,3.0(0.0), 0.0,4.0(3.0)]",
				m4.findShortestPath(d_Coordinate, b_Coordinate).toString());
	}
}
