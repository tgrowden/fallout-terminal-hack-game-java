package com.tgrowden.fallouttermainalhackgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;

class Game {
	/**
	 * The remaining guesses
	 */
	int remainingGuesses;

	Boolean isLucky;

	/**
	 * The initial number of guesses allowed
	 */
	private int initialGuesses;

	/**
	 * Whether or not the game has been solved
	 */
	Boolean solved;

	private final Random randomGenerator;

	/**
	 * The options for the game
	 */
	private ArrayList<Guess> options;

	/**
	 * The word length of the options / solution
	 */
	private int wordLength;

	private int wordListLength;

	/**
	 * The solution to the game
	 */
	String solution;


	/**
	 * Creates an instance of Game
 	 * @param wordLength The word length for the instance of Game
	 *
	 */
	Game(int wordLength, int wordListLength) {
		this(wordLength, wordListLength, 4);
	}

	/**
	 * Creates an instance of Game
	 * @param wordLength The word length for the instance of Game
	 * @param allowedGuesses The number of guesses allowed
	 */
	Game(int wordLength, int wordListLength, int allowedGuesses) {
		this.randomGenerator = new Random();
		this.wordLength = wordLength;
		this.wordListLength = wordListLength;
		this.solved = false;
		this.remainingGuesses = allowedGuesses;
		this.initialGuesses = this.remainingGuesses;
		this.isLucky = false;
		this.setup();
	}

	/**
	 * Sets up the game by creation a list of options and the answer
	 */
	private void setup() {
		ArrayList<String> possibleOptions = this.getPossibleOptions();
		this.setSolution(this.getRandomOption(possibleOptions));

		ArrayList<Guess> res = new ArrayList<>();
		do {
			String word = this.getRandomOption(possibleOptions);
			int commonality = this.findCommonCharacters(this.solution, word.toUpperCase());
			res.add(new Guess(word, commonality));
		} while (
			res.size() < this.wordListLength
		);
		res.add(new Guess(this.solution, this.solution.length()));

		Collections.shuffle(res);
		this.options = res;
	}

	/**
	 * Sets the solution
	 * @param word The solution to the Game instance
	 */
	private void setSolution(String word) {
		this.solution = word.toUpperCase();
	}

	/**
	 * Gets a random value from passed ArrayList<String>
	 * @param possibleOptions The ArrayList from which to retrieve a random item
	 * @return String
	 */
	private String getRandomOption(ArrayList<String> possibleOptions) {
		int index = this.randomGenerator.nextInt(possibleOptions.size());
		String item = possibleOptions.get(index);

		return item;
	}

	/**
	 * Determines the number of common characters between two strings
	 * @param s1 The first string
	 * @param s2 The second string
	 * @return int The number of common characters between `s1` and `s2`
	 */
	private int findCommonCharacters(String s1, String s2) {
		int count = 0;
		for(int i = 0; i < s1.length() && i < s2.length(); i++) {
			if(s1.charAt(i) == s2.charAt(i)){
				count++;
			}
		}

		return count;
	}

	/**
	 * Left-pads a string with `padding` padding using char `padder`
	 * @param text The text to pad
	 * @param padding The number of characters to pad the string
	 * @param padder The character used for padding
	 * @return String The left-padded string
	 */
	public String leftPad(String text, int padding, char padder) {
		char[] chars = new char[padding];
		Arrays.fill(chars, padder);
		String padded = new String(chars);
		return padded.substring(text.length()) + text;
	}

	/**
	 * leftPad() with default padder of ' '
	 * @param text
	 * @param padding
	 * @return String The padded string
	 */
	public String leftPad(String text, int padding) {
		return this.leftPad(text, padding, ' ');
	}

	/**
	 * @return String The absolute path to the wordlist text file
	 */
	private String getWordlistPath() {
		File file = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String wordlistPath = file.getParentFile().getParentFile().getParentFile().getParent() + "/wordlist.txt";

		return wordlistPath;
	}

	/**
	 * Gets the possible options (i.e. words which are of the proper word length)
	 * @return ArrayList<String> The array list
	 */
	private ArrayList<String> getPossibleOptions() {
		ArrayList<String> list = new ArrayList<>();
		String wordlistPath = this.getWordlistPath();
		Scanner s = null;
		try {
			s = new Scanner(new File(wordlistPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assert s != null;
		while (s.hasNext()) {
			String next = s.next();
			if (next.length() == this.wordLength) {
				list.add(next);
			}
		}

		return list;
	}

	/**
	 * Get the remaining guesses
	 * @return bool The guesses
	 */
	public int getGuesses() {
		return this.remainingGuesses;
	}

	/**
	 * Displays the options property formatted
	 */
	public void displayOptions() {
		int count = 0;
		for (Guess g : this.options) {
			if (!g.guessed) {
				count++;
				System.out.printf(
					"%s)%s\n",
					this.leftPad(String.valueOf(count), 2),
					this.leftPad(g.text, this.wordLength + 2)
				);
			} else {
				System.out.printf("%s (%s)\n", this.leftPad(g.text, this.wordLength + 5), g.commonChars);
			}
		}
		System.out.println(" r)  reset");
		System.out.println(" q)  quit");
		if (this.allowsHint()) {
			System.out.println(" h)  hint");
		}
	}

	/**
	 * Determine whether or not a "hint" option is allowed
	 * @return
	 */
	private Boolean allowsHint() {
		return this.remainingGuesses < this.initialGuesses;
	}

	/**
	 * Attempts to solve the Game instance
	 * @param guess The guess
	 */
	public void attempt(String guess) {
		if (guess.toLowerCase().equals("r")) {
			System.out.println("\nResetting tries remaining...\n");
			this.resetGuesses();
		} else if (guess.toLowerCase().equals("q")) {
			System.out.println("Exiting...");
			System.exit(0);
		} else if (this.allowsHint() && guess.toLowerCase().equals("h")) {
			this.displayHint();
		} else {
			ArrayList<Guess> filtered = new ArrayList<>();
			for (Guess g : this.options) {
				if (!g.guessed) {
					filtered.add(g);
				}
			}
			try {
				int index = Integer.valueOf(guess);
				int optionsIndex = this.options.indexOf(filtered.get(index - 1));
				Guess guessed = this.options.get(optionsIndex);
				guessed.guessed = true;

				if (guessed.text.equals(this.solution)) {
					if (this.initialGuesses == this.remainingGuesses) {
						this.isLucky = true;
					} else {
						this.solved = true;
					}
				} else {
					this.remainingGuesses--;
				}
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.out.println("\nNot a valid guess...\n");
			}
		}
	}

	/**
	 * Display a hint
	 * @todo Handle this logic
	 */
	private void displayHint() {
		System.out.println("This has not been implemented yet...");
	}

	/**
	 * Resets the guesses
	 */
	private void resetGuesses() {
		this.remainingGuesses = this.initialGuesses;
	}
}
