/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw3;


/**
 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
 *
 * The first two terms of the Fibonacci sequence are both 1,
 * and each subsequent term is the sum of the previous two terms.
 *
 * @author mbolin
 */
@SuppressWarnings("nullness")
public class Fibonacci {

    /**
     * Calculates the desired term in the Fibonacci sequence.
     *
     * @param n the index of the desired term; the first index of the sequence is 0
     * @return the <var>n</var>th term in the Fibonacci sequence
     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
     */
    public int getFibTerm(int n) {
        if (n < 0) {// if less than 0, it will throw exceptions
            throw new IllegalArgumentException(n + " is negative");
        } else if (n <= 1) {// since the first index is 0, so the base case are index 0 and 1.
            return 1;
        } else {// The next Fibonacci is adding the previous 2 Fibonacci, not subtracting.
            return getFibTerm(n - 1) + getFibTerm(n - 2);
        }
    }

}
