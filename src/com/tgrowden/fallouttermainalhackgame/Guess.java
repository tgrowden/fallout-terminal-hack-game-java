package com.tgrowden.fallouttermainalhackgame;

class Guess {
	/**
	 * The text (i.e. the option)
	 */
	public String text;

	/**
	 * Whether or not this has been guessed
	 */
	public Boolean guessed;

	/**
	 * The number of characters in common with the solution
	 */
	public int commonChars;

	/**
	 * Instantiates the class
	 * @param text
	 * @param guessed
	 */
	Guess(String text, int commonChars, Boolean guessed) {
		this.text = text.toUpperCase();
		this.commonChars = commonChars;
		this.guessed = guessed;
	}

	/**
	 * Instantiates the class
	 * Overloading to allow for default value of `guessed: false`
	 * @param text
	 */
	Guess(String text, int commonChars) {
		this(text, commonChars, false);
	}
}
