package ie.atu.sw;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;


/**
 * 
 * The {@code SentAnalysis} class contains methods that take in the words and
 * lexicons processed and analysis the data and computing a sentiment score.
 * 
 * @author Vanessa Tolentino
 * @version 1.0
 *
 */
public class SentAnalysis {

	// Variables with class level scope
	// new set to store compared words(keys)
	Set<String> commonWords = new ConcurrentSkipListSet<>();

	// The map containing lexicon and the set containing the data
	ConcurrentSkipListMap<String, Double> lexMap = LexiconReadIntoMap.getLex();
	Set<String> mySet = TextReadIntoSet.getWords();

	double score = 0;// Stores the sum of scores
	double posWords = 0; // positive word counter
	double negWords = 0; // negative word counter
	StringBuilder resultBuilder1 = new StringBuilder(); // new string builder

	/**
	 * Returns the analysed texts as a string to be able to output it to a txt file. 
	 * 
	 * Called from the Menu class and 
	 * 
	 * @param textFileName passes in the inputted name of file from user
	 * @param lexiconName  passes in the inputted name of lexicon from user
	 * @return this returns the sentiment analysis to a string
	 * @throws Throwable If an error occurs during file processing
	 */
	public String sentAnalysis(String textFileName, String lexiconName) throws Throwable {

		// new instance of Sentiment Analysis gets the results
		SentAnalysis fileNames = new SentAnalysis();// New instance of the SentAnalysis class
		String sent = fileNames.compare();// calls the method compare and starts the analysis
		return sent.toString();
	}

	/**
	 * Takes in the parsed text and lexicon and compares them to
	 * eachother. 
	 * 
	 * The analysis result is added to a string builder and returned
	 * 
	 * @return The StringBuilder and converts it to a string
	 */
	public String compare() {
		StringBuilder resultBuilder = new StringBuilder();

		// An executor is called and is surrounded as a resource for the try block
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			executor.submit(() -> { 

				// new set to store common words from the lexicon and the tweets
				Set<String> lexiconKeysCopy = lexMap.keySet(); //O(n) gets the keys in the map and depends on how many keys there are

				// only keeps words that are the same (unortunately doesn't account for word
				// suffix')
				//This forum was used to to help write the code
				// https://stackoverflow.com/questions/19785438/compare-set-list-in-my-case
				lexiconKeysCopy.retainAll(mySet); // O(n) it compares the data structures to eachother

				// update the commonWords class variable
				this.commonWords = lexiconKeysCopy;
				resultBuilder.append("Common words between Lexicon and Tweets: " + "\n" + commonWords + "\n");

				// calls the computePolarity method and appends the reults of its
				// StringBuilder return to the StringBuilder of this method
				resultBuilder.append(computePolarity());

			});
		}
		return resultBuilder.toString();// converts back to a string
	}

	/**
	 * Computes the polarity and executed by adding all the values of each common word. 
	 * 
	 * The positive and negative words are also noted. 
	 * 
	 * @return the stringbuilder analysis results are returned
	 */
	public StringBuilder computePolarity() {
		StringBuilder resultBuilder2 = new StringBuilder();

		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			executor.submit(() -> {

				for (String words : commonWords) {// O(n) - loop over the new set and so it depends on how
													// much is in the file to process it
					// within the map, get the value of the words that are common
					double value = lexMap.get(words);

					// update the polarity by adding all the values
					this.score = score + value;

					if (value > 0.00d) {
						this.posWords++;
					}
					if (value < 0.00d) {
						this.negWords++;
					}
				}
				// StringBuilder2 is updated and appended to
				this.resultBuilder1 = resultBuilder2.append("Polarity(SaS): " + score + "\n");
				this.resultBuilder1 = resultBuilder2.append("Number of positive words: " + posWords + "\n");
				this.resultBuilder1 = resultBuilder2.append("Number of negative words: " + negWords + "\n");

				// Prints same to console
				System.out.println("polarity(SaS): " + score);
				System.out.println("number of positive words: " + posWords);
				System.out.println("number of negative words: " + negWords + "\r");
			});
		}

		return resultBuilder2;
	}
}