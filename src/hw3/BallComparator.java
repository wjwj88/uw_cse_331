package hw3;

import java.util.Comparator;

public class BallComparator implements Comparator<Ball>{

	public int compare (Ball b1,Ball b2) {
		return (int)((b1.getVolume()-b2.getVolume())*10);
	}
}
