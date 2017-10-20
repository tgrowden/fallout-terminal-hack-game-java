package com.tgrowden.fallouttermainalhackgame;

public class GameParams {
	private int wordLength;
	private int wordListLength;
	private int allowedGuesses;

	GameParams(int wordLength, int wordListLength) {
		this(wordLength, wordListLength, 4);
	}

	GameParams(int wordLength, int wordListLength, int allowedGuesses) {
		this.wordLength = wordLength;
		this.wordListLength = wordListLength;
		this.allowedGuesses = allowedGuesses;
	}

	public int getWordLength() {
		return this.wordLength;
	}

	public int getWordListLength() {
		return this.wordListLength;
	}

	public int getAllowedGuesses() {
		return this.allowedGuesses;
	}
}
