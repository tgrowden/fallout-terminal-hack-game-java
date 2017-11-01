import java.net.URL;

public class GameParams {
	/**
	 * The URL of the word list
	 */
	private final URL wordListUrl;
	/**
	 * The word length
	 */
	private final int wordLength;
	/**
	 * The word list length
	 */
	private final int wordListLength;
	/**
	 * The allowed guesses
	 */
	private final int allowedGuesses;

	/**
	 * Overloaded constructor with default `allowedGuesses`
	 * @param wordLength
	 * @param wordListLength
	 */
	GameParams(URL wordListUrl, int wordLength, int wordListLength) {
		this(wordListUrl, wordLength, wordListLength, 4);
	}

	/**
	 * Creates an instance of `GameParams`
	 * @param wordLength
	 * @param wordListLength
	 * @param allowedGuesses
	 */
	GameParams(URL wordListUrl, int wordLength, int wordListLength, int allowedGuesses) {
		this.wordListUrl = wordListUrl;
		this.wordLength = wordLength;
		this.wordListLength = wordListLength;
		this.allowedGuesses = allowedGuesses;
	}

	/**
	 * wordListUrl getter
	 * @return wordListUrl
	 */
	public URL getWordListUrl() {
		return wordListUrl;
	}

	/**
	 * wordLength getter
	 * @return wordLength
	 */
	public int getWordLength() {
		return this.wordLength;
	}

	/**
	 * wordListLength getter
	 * @return wordListLength
	 */
	public int getWordListLength() {
		return this.wordListLength;
	}

	/**
	 * allowedGuesses getter
	 * @return allowedGuesses
	 */
	public int getAllowedGuesses() {
		return this.allowedGuesses;
	}
}