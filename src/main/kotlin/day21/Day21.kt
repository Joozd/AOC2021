package day21

import common.Solution
import common.extensions.mapMultiThreaded
import common.extensions.words
import kotlin.math.max

class Day21: Solution {
    override val day = 21
    private val input = inputLines().map {it.words().last().toInt()}

    private val diracDice = DiracDice(3)

    /**
     * A game with a 100 sided deterministic dice
     */
    override fun answer1(): Any {
        val game = GameState(input[0], input[1], 1000)
        val dice = Dice()

        while(!game.hasWinner()){
            game.addRoll(0, dice.roll())
            if (game.hasWinner()) {
                return game.score(dice.rolls)
            }
            game.addRoll(1, dice.roll())
        }
        return game.score(dice.rolls)
    }

    /**
     * All possible games with 3 3-sided dice
     */
    override fun answer2(): Any {
        val initialGameState = GameState(input[0], input[1], 21)
        return playDiracDiceMultithreaded(initialGameState, true).let{
            max(it.first, it.second)
        }
    }

    private fun playDiracDiceMultithreaded(gameState: GameState, playerOnesTurn: Boolean): Pair<Long, Long> =
        diracDice.roll().entries.mapMultiThreaded{ rollWithWeight ->
            (getResultForRoll(gameState.copy(), playerOnesTurn, rollWithWeight.key) * rollWithWeight.value)
        }.sum()

    private fun playDiracDice(gameState: GameState, playerOnesTurn: Boolean): Pair<Long, Long> =
        diracDice.roll().map{ rollWithWeight ->
            (getResultForRoll(gameState.copy(), playerOnesTurn, rollWithWeight.key) * rollWithWeight.value)
        }.sum()


    /**
     * Get the result for a single roll. If this roll leads to a victory, return a pair of (1,0) or (0,1), depending on who won.
     * Else, play another round. and return the results of that round.
     */
    private fun getResultForRoll(gameState: GameState, playerOnesTurn: Boolean, roll: Int): Pair<Long, Long>{
        gameState.addRoll(if (playerOnesTurn) 0 else 1, roll)
        return if (gameState.hasWinner()){
            if (gameState.playerOneWon()) 1L to 0L else 0L to 1L
        }
        else playDiracDice(gameState, !playerOnesTurn)
    }

    /**
     * Functions to add and multiply Pairs
     */
    private operator fun Pair<Long, Long>.times(n: Int) = this.first * n to this.second * n
    private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = this.first + other.first to this.second + other.second

    /**
     * Sum all values in pairs (sum all firsts and all seconds)
     */
    private fun List<Pair<Long, Long>>.sum() = this.reduce { acc, pair -> acc + pair }

}