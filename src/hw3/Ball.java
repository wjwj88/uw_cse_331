/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw3;


/**
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
@SuppressWarnings("nullness")
public class Ball {

    private double volume;

    /**
     * Constructor that creates a new ball object with the specified volume.
     * @param volume Volume of the new object.
     */
    public Ball(double volume) {
        this.volume = volume;// this.volume should be used instead of volume
    }

    /**
     * Returns the volume of the Ball.
     * @return the volume of the Ball.
     */
    public double getVolume() {
        return volume;// return volume, not 0
    }

}
