package hw4.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * SpecificationTests is a simple TestSuite that includes and runs all the tests
 * in {@link RatNumTest}, {@link RatPolyTest}, and {@link RatPolyStackTest}.
 */

@RunWith(Suite.class)
@SuiteClasses({ RatNumTest.class,
  RatTermTest.class,
  RatPolyTest.class,
  RatPolyStackTest.class })
@SuppressWarnings("nullness")
public final class SpecificationTests {
  // This class is a placeholder for the suite.
  // The @SuiteClasses annotation lists the elements of the suite.

   /**
    * Checks that assertions are enabled. If they are not, an error message is printed,
    * and the system exits.
    */
	public static void checkAssertsEnabled() {
		try {
			assert false;

			// assertions are not enabled
			System.err.println("Java Asserts are not currently enabled. Follow homework writeup instructions to enable asserts on all JUnit Test files.");
			System.exit(1);

		} catch (AssertionError e) {
			// do nothing
			// assertions are enabled
		}
	}
}
