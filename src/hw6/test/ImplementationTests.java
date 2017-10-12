package hw6.test;// generic version-Jun Wang 5:00

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * ImplementationTests is a test suite used to encapsulate all
 * tests specific to your implementation of this problem set.
 *
 * For instance, unit tests for your individual methods would
 * go here.
 */

@RunWith(Suite.class)
@SuiteClasses({ CheckAsserts.class, PathFinderTest.class })
public final class ImplementationTests
{
  //this class is a placeholder for the suite, so it has no members.
}

