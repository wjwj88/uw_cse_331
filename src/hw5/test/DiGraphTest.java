package hw5.test; //generic version-Jun Wang 5:00

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hw5.*;
import hw6.MarvelPaths;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the DiGraph class.
 * <p>
 */
@SuppressWarnings("nullness")

public final class DiGraphTest {
		    
	// construct new DiGraph	
	DiGraph d1,d2,d3,d4,d5,d6,d1_copy,d2_copy,d3_copy;
	// create new EdgesOfNodes
	EdgesOfNode ee1,ee2,ee3,ee4,ee5,ee6,ee7,ee8,ee9,ee10;
	
	Comparator c=MarvelPaths.sortEdgeComparator();
	ArrayList<EdgesOfNode> a1,a2,a3,a4,a5,a6,a7;
	@Before
	public void setUp(){		
		
		//d1, d1_copy is a graph with no nodes and edges
		d1=new DiGraph();
		d1_copy=new DiGraph();
		
		//d2, d2_copy is a graph with one node n1 and no edges
		d2=new DiGraph();
		d2_copy=new DiGraph();
		d2.addNode("n1");
		d2_copy.addNode("n1");
		
		//d3, d3_copy is a graph with one node n1 and two edges e1, e2 connecting itself
		d3=new DiGraph();
		d3_copy=new DiGraph();
		d3.addNode("n1");
		d3.addEdge("n1", "n1", "e2");
		ee1=new EdgesOfNode("n1", "e2");
		d3.addEdge("n1", "n1", "e1");
		ee2=new EdgesOfNode("n1", "e1");
		d3_copy.addNode("n1");
		d3_copy.addEdge("n1", "n1", "e2");
		d3_copy.addEdge("n1", "n1", "e1");
		
		//d4 is a graph with node n1, n2, n3, n4, n5 and their children node are:
		//n1 : n2(e4) n1(e2) n1(e1) n3(e3) 
		//n2 : n3(e6)
		//n4 : n1(e5) n3(e7)
		//n5 : n5(e9) n5(e8) n5(e9)
		d4=new DiGraph();
		d4.addNode("n1");
		d4.addNode("n2");
		d4.addNode("n3");
		d4.addNode("n4");
		d4.addNode("n5");
		d4.addEdge("n1", "n2", "e4");
		ee3=new EdgesOfNode("n2", "e4");
		d4.addEdge("n1", "n1", "e2");
		d4.addEdge("n1", "n1", "e1");	
		d4.addEdge("n4", "n1", "e5");
		d4.addEdge("n1", "n3", "e3");
		ee4=new EdgesOfNode("n3", "e3");
		d4.addEdge("n4", "n3", "e7");
		d4.addEdge("n5", "n5", "e9");
		ee5=new EdgesOfNode("n5", "e9");
		d4.addEdge("n2", "n3", "e6");
		d4.addEdge("n1", "n2", "e4");
		ee6=new EdgesOfNode("n2", "e4");
		d4.addEdge("n5", "n5", "e8");
		d4.addEdge("n5", "n5", "e9");
		
		
		//d5 is a graph with node n1, n2, n3, n4, n5 and their children node are:
		//n1 : n2(e4) n1(e2) n1(e1) n3(e3) n2(e4)
		//n2 : n3(e6)
		//n4 : n1(e5) n3(e7)
		//n5 : n5(e9) n5(e8) n5(e9) n2(e11) n4(e10) n3(e10)
		d5=new DiGraph();
		d5.addNode("n1");
		d5.addNode("n2");
		d5.addNode("n3");
		d5.addNode("n4");
		d5.addNode("n5");
		d5.addEdge("n1", "n2", "e4");
		d5.addEdge("n1", "n1", "e2");
		d5.addEdge("n1", "n1", "e1");	
		d5.addEdge("n4", "n1", "e5");
		d5.addEdge("n1", "n3", "e3");
		d5.addEdge("n4", "n3", "e7");
		d5.addEdge("n5", "n5", "e9");
		d5.addEdge("n2", "n3", "e6");
		d5.addEdge("n1", "n2", "e4");
		d5.addEdge("n5", "n5", "e8");
		d5.addEdge("n5", "n2", "e11");
		ee7=new EdgesOfNode("n2", "e11");
		d5.addEdge("n5", "n4", "e10");
		d5.addEdge("n5", "n3", "e10");
		ee8=new EdgesOfNode("n3", "e10");
		
		
		//d6 is a graph with node n1, n2, n3, n4, n5 and their children node are:
		//n1 : n2(e4) n1(e2) n1(e1) n3(e3) n2(e4)
		//n2 : n3(e6)
		//n4 : n1(e5) n3(e7)
		//n5 : n5(e9) n5(e8) n5(e9) n2(e11) n4(e10) n3(e10)
		//d6 has the same node and edges as d5, but the edges are added in different order from d5
		d6=new DiGraph();
		d6.addNode("n1");
		d6.addNode("n2");
		d6.addNode("n3");
		d6.addNode("n4");
		d6.addNode("n5");
		d6.addEdge("n1", "n2", "e4");
		d6.addEdge("n1", "n1", "e1");	
		d6.addEdge("n4", "n1", "e5");
		d6.addEdge("n1", "n3", "e3");
		d6.addEdge("n4", "n3", "e7");
		d6.addEdge("n5", "n5", "e9");
		d6.addEdge("n1", "n1", "e2");
		d6.addEdge("n2", "n3", "e6");
		d6.addEdge("n5", "n5", "e8");
		d6.addEdge("n5", "n4", "e10");
		d6.addEdge("n5", "n2", "e11");
		d6.addEdge("n5", "n3", "e10");
		
		// convert Set<EdgesOfNode> to ArrayList<EdgesOfNode>
		a1=new ArrayList<EdgesOfNode>(d3.getEdgesOfNode("n1"));
		a2=new ArrayList<EdgesOfNode>(d3_copy.getEdgesOfNode("n1"));
		a3=new ArrayList<EdgesOfNode>(d4.getEdgesOfNode("n1"));
		a4=new ArrayList<EdgesOfNode>(d5.getEdgesOfNode("n1"));
		a5=new ArrayList<EdgesOfNode>(d5.getEdgesOfNode("n5"));
		a6=new ArrayList<EdgesOfNode>(d6.getEdgesOfNode("n1"));
		a7=new ArrayList<EdgesOfNode>(d6.getEdgesOfNode("n5"));
		Collections.sort(a1,c);
		Collections.sort(a2,c);
		Collections.sort(a3,c);
		Collections.sort(a4,c);
		Collections.sort(a5,c);
		Collections.sort(a6,c);
		Collections.sort(a7,c);					
	}
			
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	isEmpty Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testIsEmpty() {
	   assertTrue(d1.isEmpty());
	   d1.addNode("n");
	   assertFalse(d1.isEmpty());
	   assertFalse(d2.isEmpty());
	   d1.removeNode("n");
	   assertTrue(d1.isEmpty());	
	   assertFalse(d3.isEmpty());
	   assertFalse(d4.isEmpty());
	   assertFalse(d5.isEmpty());
	   
	}		
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	removeNode() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testRemoveNode() {
		d2_copy.removeNode("n1");		
	    assertTrue(d2_copy.isEmpty());		
	    d2_copy.removeNode("n1");
	    assertTrue(d2_copy.isEmpty());
	    d2_copy.addNode("n1");
	    assertFalse(d2_copy.isEmpty());
	    d2_copy.addNode("n2");
	    d2_copy.addNode("n3");
	    assertFalse(d2_copy.isEmpty());
	    d2_copy.removeNode("n2");
	    d2_copy.removeNode("n3");
	    assertFalse(d2_copy.isEmpty());
	    d2_copy.removeNode("n1");
	    assertTrue(d2_copy.isEmpty());
	    d2_copy.addNode("n1");
	}		
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	getEdge() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testGetEdge() {
		assertEquals("[]", d1.getEdge("n1").toString());
		assertEquals("[]", d2.getEdge("n1").toString());
		assertEquals("[e1, e2]", d3.getEdge("n1").toString());
		assertEquals("[e1, e2, e3, e4]", d4.getEdge("n1").toString());
		assertEquals("[e10, e10, e11, e8, e9]", d5.getEdge("n5").toString());
	}
				
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	removeEdge() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testRemoveEdge() {
		d1_copy.removeEdge("n1","n1");
		assertEquals("[]", d1_copy.getEdge("n1").toString());
		d2_copy.removeEdge("n1","n1");
		assertEquals("[]", d2_copy.getEdge("n1").toString());
		d3_copy.removeEdge("n1", "n1", "e2");
		assertEquals("[e1]", d3_copy.getEdge("n1").toString());//////
		d3_copy.removeEdge("n1", "n1", "e1");
		assertEquals("[]", d3_copy.getEdge("n1").toString());
		d3_copy.addEdge("n1", "n1", "e2");
		d3_copy.addEdge("n1", "n1", "e1");
		assertEquals("[e1, e2]", d3_copy.getEdge("n1").toString());
		d3_copy.addEdge("n1", "n1", "e2");
		d3_copy.addEdge("n1", "n1", "e1");		
	}		
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	adjacentyList() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testAdjacentyList() {
		//node A is not in graph d1
		assertEquals("[]", d1.adjacentyList("A").toString());
		assertEquals("[]", d2.adjacentyList("n1").toString());
		assertEquals("[n1]", d3.adjacentyList("n1").toString());
		assertEquals("[n1, n2, n3]", d4.adjacentyList("n1").toString());
		assertEquals("[]", d4.adjacentyList("n3").toString());
		assertEquals("[n1, n3]", d4.adjacentyList("n4").toString());
		assertEquals("[n1, n2, n3]", d5.adjacentyList("n1").toString());
		assertEquals("[n2, n3, n4, n5]", d5.adjacentyList("n5").toString());	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	parentList() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testParentList() {
		//node A is not in graph d1
		assertEquals("[]", d1.parentList("A").toString());
		assertEquals("[]", d2.parentList("n1").toString());
		assertEquals("[n1]", d3.parentList("n1").toString());
		assertEquals("[n1, n4]", d4.parentList("n1").toString());
		assertEquals("[n1, n2, n4]", d4.parentList("n3").toString());
		assertEquals("[n1]", d4.parentList("n2").toString());
		assertEquals("[]", d4.parentList("n4").toString());
		assertEquals("[n1, n5]", d5.parentList("n2").toString());
		assertEquals("[n1, n4]", d5.parentList("n1").toString());
		assertEquals("[n1, n2, n4, n5]", d5.parentList("n3").toString());	
		assertEquals("[n5]", d5.parentList("n5").toString());
	}		
		
		
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	isConnected() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testIsConnected() {
		assertFalse(d2.isConnected("n1","n1"));
		assertTrue(d3.isConnected("n1","n1"));
		assertFalse(d3.isConnected("n1","n2"));
		assertTrue(d4.isConnected("n1","n1"));
		assertTrue(d4.isConnected("n1","n2"));
		assertFalse(d4.isConnected("n1","n4"));
		assertFalse(d4.isConnected("n3","n1"));
		assertTrue(d4.isConnected("n2","n3"));
		assertFalse(d4.isConnected("n3","n2"));
		assertFalse(d4.isConnected("n2","n4"));
		assertFalse(d4.isConnected("n4","n2"));
		assertTrue(d5.isConnected("n2","n3"));
		assertFalse(d5.isConnected("n3","n2"));
		assertFalse(d5.isConnected("n1","n5"));
		assertFalse(d5.isConnected("n5","n1"));
		assertTrue(d5.isConnected("n5","n3"));
		assertFalse(d5.isConnected("n3","n5"));			
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	getEdgesOfNode() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testGetEdgesOfNode() {
		assertEquals("[n1(e1), n1(e2)]",a1.toString());////
		assertEquals("[n1(e1), n1(e2)]",a2.toString());
		assertEquals("[n1(e1), n1(e2), n2(e4), n3(e3)]",a3.toString());
		assertEquals("[n1(e1), n1(e2), n2(e4), n3(e3)]",a4.toString());
		assertEquals("[n2(e11), n3(e10), n4(e10), n5(e8), n5(e9)]",a5.toString());
		assertEquals("[n1(e1), n1(e2), n2(e4), n3(e3)]",a6.toString());
		assertEquals("[n2(e11), n3(e10), n4(e10), n5(e8), n5(e9)]",a7.toString());			
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	NodeList() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testNodeList() {
		assertEquals("[]", d1.NodeList().toString());
		assertEquals("[n1]", d2.NodeList().toString());
		assertEquals("[n1]", d3.NodeList().toString());
		assertEquals("[n1, n2, n3, n4, n5]", d4.NodeList().toString());
		assertEquals("[n1, n2, n3, n4, n5]", d5.NodeList().toString());
	}
			
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	size() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testSize() {
		assertEquals(0, d1.size());
		assertEquals(1, d2.size());
		assertEquals(1, d3.size());
		assertEquals(5, d4.size());
		assertEquals(5, d5.size());
	}
			
			
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	equals() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testEquals() {
		assertFalse(d1.equals(null));
		assertTrue(d1.equals(d1));
		assertFalse(d1.equals(d2));
		d2_copy.removeNode("n1");
		assertTrue(d1.equals(d2_copy));
		assertTrue(d2_copy.equals(d1));
		assertFalse(d3.equals(d4));
		assertFalse(d4.equals(d3));
		assertFalse(d4.equals(d5));
		assertTrue(d5.equals(d6));
		assertTrue(d6.equals(d5));
		d6.removeNode("n5");
		assertFalse(d5.equals(d6));
		assertFalse(d6.equals(d5));
		assertFalse(d4.equals(d6));
		assertFalse(d6.equals(d4));		
	}
	
}




