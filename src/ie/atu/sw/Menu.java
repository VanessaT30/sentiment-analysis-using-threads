package ie.atu.sw;

import java.io.File;

import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code Menu} class displays a menu to the user which delegates and calls
 * the sentiment analysis application.
 * 
 * According to the number in the menu chosen, users can specify the name of the
 * text file data, output name, lexicon directory name and finally, conduct the
 * sentiment analysis itself.
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 * @since 2.1
 * 
 */

public class Menu {
	// Instance variables
	private Scanner s = null;
	private boolean keepRunning = true;
	private String textFileName = "100TwitterUsers";
	private String output = "SentAnal";
	private String lexiconName = "lexicons";
	private String sentAnal = null;
	
	/**
	 * This is a constructor method holding the scanner variable
	 */
	public Menu() {
		s = new Scanner(System.in);
	}

	/**
	 * Starts the menu and allows users to interact with it.
	 * 
	 * Extended switch statement used to switch on users choices.
	 * 
	 * @throws Throwable if an error occurs while taking in a choice
	 */
	public void start() throws Throwable {
		while (keepRunning) {
			menuDisplay();

			int choice = Integer.parseInt(s.next()); // converts scanner return to an int
			switch (choice) {
			case 1 -> textSet();
			case 2 -> outputFile();
			case 3 -> lexiconMap();
			case 4 -> result();
			case 5 -> System.exit(0);
			default -> System.out.println("Error: Invalid Selection.");
			}
		}
		System.out.println("[INFO] Exiting Application!");
	}

	/**
	 * Obtain and saves the file directory name chosen by the user.
	 * 
	 * @throws IOException
	 */
	private void textSet() throws IOException {
		System.out.println("[1] You Have Chosen to Specify a Text File");
		System.out.println("Please specify the name of a txt file you would like to analyse");

		Scanner s = new Scanner(System.in); // blocking taking in user input
		String txtFile = s.nextLine(); // Stores user input into inputFileName
		System.out.println("You have chosen: " + txtFile + ".");
		System.out.println();

		// Check to see if file directory chosen is valid, if so, continue
		File directory = new File(txtFile);
		if (!directory.exists()) {
			System.out.println("Directory does not exist. Please try again");
		} else if (directory.length() == 0) {
			System.out.println("Directory does not contain any files.");
		} else {
		}

		// Assigns textfile name retrieved to the instance variable input
		this.textFileName = txtFile;

		// Method called to print out the file names in the directory
		IOHandling.printTxtFiles(textFileName);
	}

	/**
	 * Obtains the name the user desires their output file to be called.
	 * 
	 * @throws Exception
	 */
	private void outputFile() throws Exception {
		System.out.println("[INFO] You Have Chosen Output File Name: ");
		System.out.println(
				"Please specify the name of a file directory you would like the sentiment analysis to output to");

		Scanner s = new Scanner(System.in); // blocking taking in output name
		String outputDirName = s.nextLine(); // Stores user input into outputDirName
		System.out.println();

		this.output = outputDirName; // assigns the name of the file to the instance variable output
		System.out.println("You have chosen: " + output + ".txt" + " as the name of your encryption output file");
	}


	/**
	 * Takes in the lexicon directory from user.
	 * 
	 * Assigns name to the instance variable lexiconName in the current class
	 * 
	 * @throws Exception
	 */
	private void lexiconMap() throws Exception {
		System.out.println("[INFO] You Have Chosen to Configure a lexicon: ");
		System.out.println("Please specify the name of a file directory you would like to analyse the data file with");

		Scanner s = new Scanner(System.in); // blocking taking in output name
		String lexName = s.nextLine(); // Stores user input into outputDirName
		System.out.println();

		this.lexiconName = lexName; // assigns the name of the file to the instance variable output
		System.out.println("You have chosen: " + lexiconName + " as your lexicon");

		IOHandling.printTxtFiles(lexiconName);
		// conditions to test if other values have been set yet,
		// if not those methods will be called
	}

	/**
	 * Conducts the entire analysis according to variable values retrieved.
	 * 
	 * Conditions to make sure all other variables are not empty and updates the
	 * sentAnal variable. These variables can be changed again if needed by simply
	 * choosing that option again.
	 * 
	 * @throws Throwable
	 */
	private void result() throws Throwable {
		System.out.println("[INFO] You Have Chosen to Decrypt: ");

		// conditions if other values have not been set yet
		if (textFileName == null) {
			System.out.println("invalid: input not set, please try again");

		} else if (output == null) {
			System.out.println("invalid: output not set, please try again");
			outputFile();

		} else if (lexiconName == null) {// || URL == null
			System.out.println("invalid: encryption not set, please try again");
			lexiconMap();
		} else {

			LexiconReadIntoMap files = new LexiconReadIntoMap();

			files.getTxtFiles(lexiconName);
			LexiconReadIntoMap.getLex();

			TextReadIntoSet txtFileNames = new TextReadIntoSet();
			txtFileNames.getTxtFiles(textFileName);
			TextReadIntoSet.getWords();

			// new instance of Sentiment Analysis gets the results
			SentAnalysis fileNames = new SentAnalysis();
			String Sent = fileNames.sentAnalysis(lexiconName, textFileName);

			this.sentAnal = Sent;
			IOHandling.writeToFile(sentAnal, output + ".txt");
		}
	}

	/**
	 * Displays the menu to the user
	 * 
	 * @throws Exception if an error occurs
	 */
	public void menuDisplay() throws Exception {
		System.out.println("************************************************************");
		System.out.println("***     ATU - Dept. Computer Science & Applied Physics   ***");
		System.out.println("***                                                      ***");
		System.out.println("***               Lexical Sentiment Analysis             ***");
		System.out.println("***                                                      ***");
		System.out.println("***                  By: Vanessa Tolentino               ***");
		System.out.println("***                                                      ***");
		System.out.println("************************************************************");
		System.out.println("  (1) Specify a Text File");
		System.out.println("  (2) Specify an Output File Default: ./out.txt");
		System.out.println("  (3) Configure Lexicons");
		System.out.println("  (4) Excecute Analyse and Report");
		System.out.println("  (5) Quit ");
	}
}
