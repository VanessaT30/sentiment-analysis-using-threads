package ie.atu.sw;

/**
 * The {@code Runner} class is the starting point that calls the sentiment
 * analysis application
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 *
 */
public class Runner {

	/**
	 * Starts the chain of command
	 * 
	 * @param args takes in a string array of arguments
	 * @throws Throwable if an error occurs it will be caught
	 */
	public static void main(String[] args) throws Throwable {

		// new instance of Menu class: first method that runs the gui
		Menu m = new Menu();
		m.start();
	}
}
