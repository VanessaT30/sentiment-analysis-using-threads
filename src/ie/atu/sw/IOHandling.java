package ie.atu.sw;

import java.io.*;

/**
 * 
 * The {@code IOHandling} class outputs the sentiment analysis into another
 * TXT file. 
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 * 
 */
public class IOHandling {

	/**
	 * Prints out the contents of input text files the user has inputted
	 * 
	 * @param input the name of the directory chosen
	 * @return returns the names of files in the directory
	 * @throws IOException will be thrown when an error occurs
	 */
	public static File printTxtFiles(String input) throws IOException {
		// New instance object of type file
		File directoryPath = new File(input);

		// loop to List of all files int the directory
		if (input == null) {
			System.out.println("Error: File not found");
		}
		// list all files in the directory and print them.
		String contents[] = directoryPath.list();
		System.out.println("Below is a list of the .txt files in the directory you have chosen: ");
		for (int i = 0; i < contents.length; i++) { // O(n)looping over the contents of the directory
													// and printing it out, so it depends on how many
													// files in the directory
			System.out.println(contents[i]);
		}
		return directoryPath;
	}

	/**
	 * Writes out the analysed text passed to a text file
	 * 
	 * @param analysedFiles Takes in already analysed texts
	 * @param writeToFileName Takes in the name of the file the user has chosen
	 * @throws Exception if an error occurs while writing to a file
	 */
	// Writes the already parsed and analysed report to a txt file
	public static void writeToFile(String analysedFiles, String writeToFileName) throws Exception {
		try (BufferedWriter fw = new BufferedWriter(new FileWriter(writeToFileName))) {

			// writes the analysed file to the named text file
			fw.write(analysedFiles);//O(n) depends on how much is in the file to be written
			// closes the connection safely
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// exceptions will be thrown if theres an error writing to file
			throw new Exception("[Error] Cannot load data from " + writeToFileName + ". " + e.getMessage());
		}
	}
}
