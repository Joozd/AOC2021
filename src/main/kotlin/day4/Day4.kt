package day4

import common.Solution

class Day4: Solution {
    override val day = 4

    private val input = inputLines()

    private lateinit var drawnNumbers: List<Int>

    /**
     * Prepare is done in this function instead of in constructor for timing purposes
     */
    override fun prepare() {
        drawnNumbers = input.first().split(",").map{it.toInt()}
    }

    /**
     * Answer 1:
     * - Find the winning Bingo Card by playing the game:
     *  -> Draw a number, and mark it on all bingo cards
     *  -> Check all cards to see if they are winning, if so, return the score
     *  -> repeat until out of numbers or a winner is picked
     */
    override fun answer1(): Any{
        val bingoCards = makeBingoCards()
        drawnNumbers.forEach { drawnNumber ->
            bingoCards.forEach { bingoCard ->
                bingoCard.mark(drawnNumber)?.let{
                    return it
                }
            }
        }
        return "No winning card found"
    }

    /**
     * Answer 2:
     * - Find the losing Bingo Card
     *  -> Keep playing and removing winning cards until there is only one card left.
     * - Calculate the score on that card
     */
    override fun answer2(): Any {
        val bingoCards = makeBingoCards().toMutableList()
        drawnNumbers.forEach { drawnNumber ->
            val cardsThisRound = bingoCards.toList() // copy so we don't change a list while iterating over it
            cardsThisRound.forEach { bingoCard ->
                bingoCard.mark(drawnNumber)?.let {
                    if (bingoCards.size == 1)
                        return it
                    else
                        bingoCards.remove(bingoCard)
                }
            }
        }
        return "No losing card found"
    }

    /**
     * make [BingoCard] objects from [input]
     */
    private fun makeBingoCards(): List<BingoCard>{
        val allCardStrings = input.drop(2).joinToString("\n").split("\n\n")
        return allCardStrings.map { BingoCard.ofCardString (it)}
    }
}