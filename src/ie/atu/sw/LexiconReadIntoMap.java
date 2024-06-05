package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;

/**
 * 
 * This {@code LexiconReadIntoMap} class <i>implements</i> the {@code GetFiles}
 * interface and contains methods that reads in the lexicon and processes them into a
 * concurrent skip list map.
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 *
 */
public class LexiconReadIntoMap implements GetFiles {

	// new instance of data structure that stores the lexicon
	private static ConcurrentSkipListMap<String, Double> lex = new ConcurrentSkipListMap<>();

	/**
	 * Retrieves text files and then calls the process method to process files from
	 * the given directory.
	 * 
	 * @param input The name of the directory and then retieves the text files.
	 * @return input name
	 * @throws Throwable If an error occurs during file processing.
	 */
	@Override
	public String getTxtFiles(String input) throws Throwable {
		File directoryPath = new File(input);

		// checks if file exists
		if (input == null) {
			System.out.println("Error: File not found");
		}
		// loop to List all of the files in the directories
		String contents[] = directoryPath.list();
		System.out.println("Below is a list of the .txt files in the directory you have chosen: ");
		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]); // O(n) loop depends on the amount of files in the directory.

			// calls the process method with full file path
			process(System.getProperty("user.dir") + "/" + input + "/" + contents[i]);
		}
		return input;
	}

	/**
	 * Process the contents of a TXT file and populate the lexicon map.
	 *
	 * @param dirName The full path to the TXT file.
	 * @throws FileNotFoundException If the specified file is not found.
	 */
	@Override
	public void process(String dirName) throws FileNotFoundException {
		// Process the contents of the txt file
		File directory = new File(dirName);

		// Use try-with-resources to ensure resources are closed
		try (Scanner scan = new Scanner(directory)) {

			// Use executor to ensure concurrent processing of tasks
			try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
				// given each task to a thread
				executor.submit(() -> {
					while (scan.hasNextLine()) {// O(n) it depends on how many lines there are to parse

						// split the line using the "," delimiter and inputs the word and its
						// given rating into the map
						
						//These rosources were used to assist in writing the code
						//https://sentry.io/answers/iterate-hashmap-java/#:~:text=Perhaps%20the%20most%20straightforward%20approach,or%20entries%20in%20the%20HashMap.
						//https://stackoverflow.com/questions/6585088/scanning-file-input-into-hashmap-in-java
						String[] line = scan.nextLine().split(",");
						lex.put(line[0], Double.parseDouble(line[1]));
						System.out.println(Thread.currentThread().getName() + " processing file: " + dirName);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//        
	}

	/**
	 * Gets the lexicon map.
	 * 
	 * @return the concurrent map containing the lexicon data
	 */
	protected static ConcurrentSkipListMap<String, Double> getLex() {
		return lex; // O(1) - simply takes in the map and returns it
	}
}
