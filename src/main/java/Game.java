import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
	/**
	 * The remaining guesses
	 */
	int remainingGuesses;

	/**
	 * Whether or not the instance of Game is considered "lucky" (i.e. guesses correctly on first attempt)
	 */
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

	/**
	 * Whether or not the guesses have been reset
	 */
	private Boolean hasBeenReset;

	/**
	 * The length of the word list
	 */
	private int wordListLength;

	/**
	 * The URL of the word list
	 */
	private URL wordListUrl;

	/**
	 * The solution to the game
	 */
	String solution;

	/**
	 * Creates an instance of Game
	 * @param gameParams The Game Params
	 */
	Game(GameParams gameParams) {
		this.randomGenerator = new Random();
		this.wordListUrl = gameParams.getWordListUrl();
		this.wordLength = gameParams.getWordLength();
		this.wordListLength = gameParams.getWordListLength();
		this.remainingGuesses = gameParams.getAllowedGuesses();
		this.initialGuesses = this.remainingGuesses;
		this.solved = false;
		this.isLucky = false;
		this.hasBeenReset = false;
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
			int commonality = StringUtils.findCommonCharacters(this.solution, word.toUpperCase());
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
	 * Gets the possible options (i.e. words which are of the proper word length)
	 * @return ArrayList<String> The array list
	 */
	private ArrayList<String> getPossibleOptions() {
		ArrayList<String> list = new ArrayList<>();
		Scanner s = null;
		try {
			s = new Scanner(wordListUrl.openStream());
		} catch (IOException e) {
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
						StringUtils.leftPad(String.valueOf(count), 2),
						StringUtils.leftPad(g.text, this.wordLength + 2)
				);
			} else {
				System.out.printf("%s (%s)\n", StringUtils.leftPad(g.text, this.wordLength + 5), g.commonChars);
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
		return false;
		// return this.remainingGuesses < this.initialGuesses;
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
					if (this.initialGuesses == this.remainingGuesses && !this.hasBeenReset) {
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
		this.hasBeenReset = true;
	}

	public Boolean wasReset() {
		return this.hasBeenReset;
	}
}
