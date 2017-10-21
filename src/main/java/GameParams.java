public class GameParams {
	/**
	 * The word length
	 */
	private int wordLength;
	/**
	 * The word list length
	 */
	private int wordListLength;
	/**
	 * The allowed guesses
	 */
	private int allowedGuesses;

	/**
	 * Overloaded constructor with default `allowedGuesses`
	 * @param wordLength
	 * @param wordListLength
	 */
	GameParams(int wordLength, int wordListLength) {
		this(wordLength, wordListLength, 4);
	}

	/**
	 * Creates an instance of `GameParams`
	 * @param wordLength
	 * @param wordListLength
	 * @param allowedGuesses
	 */
	GameParams(int wordLength, int wordListLength, int allowedGuesses) {
		this.wordLength = wordLength;
		this.wordListLength = wordListLength;
		this.allowedGuesses = allowedGuesses;
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