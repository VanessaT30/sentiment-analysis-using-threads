package ie.atu.sw;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * The {@code TextReadIntoSet} class <i>implements</i> the {@code GetFiles}
 * interface.
 * 
 * It contains methods for retrieving/processing text files and inputting them
 * into a set of words.
 *
 * @author Vanessa Tolentino
 * @version 1.0
 */
public class TextReadIntoSet implements GetFiles {

	// New instance of ConcurrentSkipListSet to store texts
	private static Set<String> words = new ConcurrentSkipListSet<>();

	/**
	 * 
	 * Retrieves text files from the user input in Menu - with virtual threads
	 * Passes retrieved filenames to the go method to process it
	 * 
	 * @param input The name of the directory and then retieves the text files.
	 * @return The input name.
	 * @throws Throwable If an error occurs during file processing.
	 * 
	 */
	public String getTxtFiles(String input) throws Throwable {

		// try catch block to close
		try {
			File directory = new File(input);

			// Checks if the directory exists, if so, a message will be
			// deployed to user, or else it will continue
			if (!directory.exists()) {
				System.out.println("Directory does not exist. Please try again");
//			} else if (directory.length() == 0) {
//				System.out.println("Directory does not contain any files.");
			} else {

				// String array stores the list of file contents in fileNames
				String[] fileNames = directory.list();

				// Iterate through all files in the directory and prints them out
				if (fileNames != null) {

					// Create a virtual thread executor
					try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
						executor.submit(() -> {
							try {
								for (int i = 0; i < fileNames.length; i++) { // O(n) loop depends how many files in the
																				// directory

									System.out.println(fileNames[i]);

									// calls the go method and concatenates the full directory path
									go(System.getProperty("user.dir") + "/" + input + "/" + fileNames[i]);
								}
							} catch (Exception e) {
								e.printStackTrace();
							} catch (Throwable e) {
								e.printStackTrace();
							}
						});

						// Shutdown the executor after all tasks are done
						executor.shutdown();

					}
				}
			}
		} catch (Exception e) {
			System.out.println("[Error] Cannot load the data from " + input + e.getMessage());
			e.printStackTrace();
		}
		return input;

	}

	/**
	 * Processes the contents of a TXT file and updates the set of words.
	 *
	 * @param file The full file path to the TXT file.
	 * @return file that was processed
	 * @throws Throwable If an error occurs during file processing.
	 */
	public String go(String file) throws Throwable {
		try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

			// O(n)I think it depends on the number of lines in the file and then loops over
			// the words
			Files.lines(Paths.get(file)).forEach(text -> {
				scope.fork(() -> {// fork join method for virtual threads
					process(text);
					return null;
				});
			});
			scope.join();
			scope.throwIfFailed(e -> e);

			out.println(words);
			out.println(words.size());
		}
		return file;
	}

	/**
	 * Changes the words into lowercase, processes a line of text and updates the
	 * set.
	 *
	 * @param text process the lines of texts.
	 */
	@Override
	public void process(String text) {
		String s = text.toLowerCase();
		Arrays.stream(s.split("\\s+")).forEach(w -> words.add(w));// O(n) takes in the text and time complexity depends
																	// on the amount of words being added to the set
	}

	/**
	 * Get the Set of words.
	 *
	 * @return The concurrent skip list set containing the words from the text file.
	 */
	protected static Set<String> getWords() {
		return words; // O(1) simply takes in the set and returns it
	}

	/**
	 * Updates the class variable word with the set of words passed in param
	 * 
	 * @param words set of words passed in
	 * @return The updated class variable 'words'
	 */
	protected Set<String> setWords(Set<String> words) {
		return TextReadIntoSet.words = words; // O(1) updates the already processed text into the variable
	}
}