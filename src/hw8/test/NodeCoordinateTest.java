package hw8.test;

import hw8.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import hw5.test.CheckAsserts;

/**
 * This class contains a set of test cases that can be used to 
 * test the implementation of the NodeCoordinate class.
 * <p>
 */
@SuppressWarnings("nullness")
public class NodeCoordinateTest {
	
	public static final double epsilon = 0.00000001;
	// create new NodeCoordinate objects
	NodeCoordinate n1,n2,n3,n4;
	@Before
	public void setUp(){					
		n1=new NodeCoordinate(2259.7112,1715.5273);
		n2=new NodeCoordinate(2260.7214,1707.4461);
		n3=new NodeCoordinate(2217.7899,1695.5768);
		n4=new NodeCoordinate(2156.585,1675.3697);			
	}
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
		
	//////////////////////////////////////////////////////////////////////
	//// getX() Test: this is to check getting x coordinate correctly
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetX() {
		assertTrue(Math.abs(2259.7112-n1.getX()) < epsilon);
		assertTrue(Math.abs(2260.7214-n2.getX()) < epsilon);
		assertTrue(Math.abs(2217.7899-n3.getX()) < epsilon);
		assertTrue(Math.abs(2156.585-n4.getX()) < epsilon);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// getY() Test: this is to check getting y coordinate correctly
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetY() {
		assertTrue(Math.abs(1715.5273-n1.getY()) < epsilon);
		assertTrue(Math.abs(1707.4461-n2.getY()) < epsilon);
		assertTrue(Math.abs(1695.5768-n3.getY()) < epsilon);
		assertTrue(Math.abs(1675.3697-n4.getY()) < epsilon);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// getDirection() Test: this is to check getting direction correctly
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testGetDirection() {
		assertEquals("N",n1.getDirection(n2));
		assertEquals("W",n2.getDirection(n3));
		assertEquals("W",n3.getDirection(n4));
		assertEquals("E",n4.getDirection(n2));
		assertEquals("SE",n3.getDirection(n1));
	}

	//////////////////////////////////////////////////////////////////////
	//// equals() Test: this is to compare two objects for equality
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testEquals() {
		assertFalse(n1.equals(n2));
		assertFalse(n1.equals(n3));
		assertFalse(n2.equals(n4));
		assertFalse(n2.equals(n3));
		assertFalse(n3.equals(n4));
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//// toString() Test: this is to test print the coordinate correctly
	//////////////////////////////////////////////////////////////////////
	@Test
	public void testToString() {
		assertEquals("(2259.7112,1715.5273)", n1.toString());
		assertEquals("(2260.7214,1707.4461)", n2.toString());
		assertEquals("(2217.7899,1695.5768)", n3.toString());
		assertEquals("(2156.585,1675.3697)", n4.toString());
	}	
}