package hw3;
import java.util.*;

/**
 * RandomHello selects a random greeting to display to the user.
 */
public class RandomHello {

    /**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
        // YOUR CODE GOES HERE
    	Random randomGenerator = new Random();
    	int index=randomGenerator.nextInt(5);
    	String[] greetings ={"Hello World","Hola Mundo","Bonjour Monde","Hallo Welt","Ciao Mondo"};
    	return greetings[index];
    	
    }
}