package hw7.test;

import org.junit.Test;

/**
 * Checks that Java Asserts are enabled.
 */
public class CheckAsserts {

  @Test
  public void testAssertEnabled() {
    checkAssertsEnabled();
  }

  /**
   * Checks that Java Asserts are enabled. If they are not, an error message is printed,
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
