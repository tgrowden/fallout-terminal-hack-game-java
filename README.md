# Fallout Terminal Hack Game

This is a clone of [fallout_terminal_hack_game](https://github.com/brianburton/fallout_terminal_hack_game) written in Java. I take no credit for the idea.

## Playing
`$ gradle run -q`

### Todo:
* Add a "hint" option: although not documented, the original implementation of the game includes this.

### How my implementation works
---
When a user starts, an iterator beings, going through the same levels of difficulty as the original project (in that words / word lists have the same lengths). In each iteration, the following occurs:

1. A `Game` object is instantiated with the proper params (`wordLength` and `wordListLength`).
    * The list of possible words (those with the correct length) are retrieved from [wordlist.txt](wordlist.txt).
    * A random word (the solution) is chosen from that list.
    * The correct number of options are chosen at random from the list.
1. The options are displayed, and the user's input is requested.
1. The user guesses.

If the user guesses correctly on the first try, the same difficulty is repeated (this is considered a "lucky" guess). As with the original game, the similarity is displayed for selected options.

If the user runs out of remaining tries, another instance of `Game` is created with the same params.

If the user completes a round, the next difficulty is begun.
