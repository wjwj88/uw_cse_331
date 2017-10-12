package hw6.test; // generic version-Jun Wang 5:00
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hw5.*;
import hw5.EdgesOfNode;
import hw6.*;
import hw6.MarvelPaths;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * This class contains a set of test cases that can be used to test the
 * method sortEdgeComparator()(used to sort EdgesOfNode objects) of the MarvelPath class.
 * <p>
 */
@SuppressWarnings("nullness")

public final class PathFinderTest {
		    
	//construct a new ArrayList to store EdgesOfNode and some EdgesOfNodes	
	ArrayList<EdgesOfNode<String,String>> list=new ArrayList<EdgesOfNode<String,String>>();
	EdgesOfNode e1,e2,e3,e4,e5,e6;
	
	@Before
	public void setUp(){		
		
		//e1,e2,e3,e4,e5,e6 are different EdgesOfNodes  		
		//to be sorted and added to the list
		e1=new EdgesOfNode("A","C");
		e2=new EdgesOfNode("A","B");
		e3=new EdgesOfNode("E","C");
		e4=new EdgesOfNode("W","C");
		e5=new EdgesOfNode("S","B");
		e6=new EdgesOfNode("A","B");
	}			
	
	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	sortEdgeComparator() Test
	///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	//This tests cover all cases to make Comparator() return < 0, = 0, and > 0.
	public void testSortEdgeComparator() {
	   Comparator<EdgesOfNode<String,String>> c=MarvelPaths.sortEdgeComparator();
	   list.add(e1);
	   assertEquals("[A(C)]",list.toString());
	   list.add(e2);
	   Collections.sort(list,c);
	   assertEquals("[A(B), A(C)]",list.toString());
	   list.add(e3);
	   Collections.sort(list,c);
	   assertEquals("[A(B), A(C), E(C)]",list.toString());
	   list.add(e4);
	   Collections.sort(list,c);
	   assertEquals("[A(B), A(C), E(C), W(C)]",list.toString());
	   list.add(e5);
	   Collections.sort(list,c);
	   assertEquals("[A(B), A(C), E(C), S(B), W(C)]",list.toString());
	   list.add(e6);
	   Collections.sort(list,c);
	   assertEquals("[A(B), A(B), A(C), E(C), S(B), W(C)]",list.toString());
	}		

}




