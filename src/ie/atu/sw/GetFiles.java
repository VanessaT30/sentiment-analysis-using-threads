package ie.atu.sw;

import java.io.FileNotFoundException;

/**
 * 
 * An interface that declares the 2 methods that should be implemented if
 * it is used. 
 * 
 * This interface highlights what other classes should implement - 
 * retrieve the files and then parsing it.
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 *
 */
public interface GetFiles {

	/**
	 * Retrieves the text file, the chosen file(s) will then be passed over to the
	 * process method
	 * 
	 * @param input takes in the users chosen file directory
	 * @return String of all files
	 * @throws Throwable if an error occurs
	 */
	String getTxtFiles(String input) throws Throwable;

	/**
	 * 
	 * {@code process} called from within the getTxtFiles method.
	 * 
	 * @param text Input takes in the text retrieved by {@code getTxtFiles}
	 * @throws FileNotFoundException if an error occurs
	 */
	abstract void process(String text) throws FileNotFoundException;
}
