package hw8.test;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.*;

import hw5.test.CheckAsserts;
import hw5.test.DiGraphTest;

/**
 * ImplementationTests is a test suite used to encapsulate all
 * tests specific to your implementation of this problem set.
 *
 * For instance, unit tests for your individual methods would
 * go here.
 */

@RunWith(Suite.class)
@SuiteClasses({ CheckAsserts.class, NodeCoordinateTest.class,
	CampusParserTest.class, CampusPathFinderModelTest.class})

public final class ImplementationTests
{
    //this class is a placeholder for the suite, so it has no members.
}

