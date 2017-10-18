package com.tgrowden.fallouttermainalhackgame;

import java.util.Scanner;

class FalloutTerminalHackGame {
	public static void main(String[] args) {
		System.out.println("Starting...");
		Scanner scan = new Scanner(System.in);
		Game game = new Game(4);
		String userGuess;

		while (game.remainingGuesses > 0 && !game.solved) {
			game.displayOptions();
			if (game.remainingGuesses > 1) {
				System.out.printf("%s tries remaining: ", game.remainingGuesses);
			} else {
				System.out.print("Last try: ");
			}
			userGuess = scan.next();
			game.attempt(userGuess);
		}
		System.out.printf("%s... The answer was %s\n", game.solved ? "success" : "failure", game.solution );
	}
}
