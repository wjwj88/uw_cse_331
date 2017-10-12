package hw5.test;//generic version-Jun Wang 5:00

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hw5.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the EdgesOfNode class.
 * <p>
 */
@SuppressWarnings("nullness")

public final class EdgesOfNodeTest {		    
	// create new EdgesOfNodes
	EdgesOfNode ee1,ee2,ee3,ee4,ee5,ee6,ee7,ee8;
	@Before
	public void setUp(){		
		
		ee1=new EdgesOfNode("n1", "e1");
		ee2=new EdgesOfNode("n1", "e2");
		ee3=new EdgesOfNode("n2", "e1");
		ee4=new EdgesOfNode("n10", "e5");
		ee5=new EdgesOfNode("n6", "e3");
		ee6=new EdgesOfNode("n6", "e3");
		ee7=new EdgesOfNode("n7", "e4");
		ee8=new EdgesOfNode("n5", "e3");		
		
	}
			
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	getTail() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testGetTail() {
	   assertEquals("n1",ee1.getTail());
	   assertEquals("n1",ee2.getTail());
	   assertEquals("n2",ee3.getTail());
	   assertEquals("n10",ee4.getTail());
	   assertEquals("n6",ee5.getTail());
	   assertEquals("n6",ee6.getTail());
	   assertEquals("n7",ee7.getTail());
	   assertEquals("n5",ee8.getTail());
	   
	}
		
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	getEdge()Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testGetEdge() {
	   assertEquals("e1",ee1.getEdge());
	   assertEquals("e2",ee2.getEdge());
	   assertEquals("e1",ee3.getEdge());
	   assertEquals("e5",ee4.getEdge());
	   assertEquals("e3",ee5.getEdge());
	   assertEquals("e3",ee6.getEdge());
	   assertEquals("e4",ee7.getEdge());
	   assertEquals("e3",ee8.getEdge());
	   
	}		
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	toString() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testToString() {
	   assertEquals("n1(e1)",ee1.toString());
	   assertEquals("n1(e2)",ee2.toString());
	   assertEquals("n2(e1)",ee3.toString());
	   assertEquals("n10(e5)",ee4.toString());
	   assertEquals("n6(e3)",ee5.toString());
	   assertEquals("n6(e3)",ee6.toString());
	   assertEquals("n7(e4)",ee7.toString());
	   assertEquals("n5(e3)",ee8.toString());
	   
	}
						
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	equals Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void testEquals() {
		assertTrue(ee1.equals(ee1));
		assertFalse(ee1.equals(ee2));
		assertFalse(ee2.equals(ee3));
		assertFalse(ee4.equals(ee5));
		assertFalse(ee4.equals(ee6));
		assertTrue(ee5.equals(ee6));
		assertFalse(ee5.equals(ee7));
		assertFalse(ee7.equals(ee8));
		assertTrue(ee6.equals(ee5));
		assertFalse(ee1.equals(ee8));	   
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	compareTo() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	//cover all test cases including <0, =0 and >0
	public void testCompareTo() {
		assertTrue(ee1.compareTo(ee1)==0);
		assertTrue(ee1.compareTo(ee2)<0);
		assertTrue(ee2.compareTo(ee3)<0);
		assertTrue(ee4.compareTo(ee5)<0);
		assertTrue(ee4.compareTo(ee6)<0);
		assertTrue(ee5.compareTo(ee6)==0);
		assertTrue(ee5.compareTo(ee7)<0);
		assertTrue(ee7.compareTo(ee8)>0);
		assertTrue(ee6.compareTo(ee5)==0);
		assertTrue(ee1.compareTo(ee8)<0);	   
	}	
}





