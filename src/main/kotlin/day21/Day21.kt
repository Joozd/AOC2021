package day21

import common.Solution
import common.extensions.words
import kotlin.math.max

class Day21: Solution {
    override val day = 21
    private val input = inputLines().map {it.words().last().toInt()}
    private val testInput = listOf(4,8)

    private val diracDice = DiracDice(3)

    override fun answer1(): Any {
        val game = GameState(testInput, 1000)
        val dice = Dice()

        while(!game.hasWinner()){
            game.addRoll(1, dice.roll())
            if (game.hasWinner()) {
                println("player 1 won: $game")
                println("Rolls: ${dice.rolls}")
                return game.score(dice.rolls)
            }
            game.addRoll(2, dice.roll())
        }
        return game.score(dice.rolls)
    }

    override fun answer2(): Any {
        val initialGameState = GameState(input, 21)
        return playDiracDice(initialGameState, true).let{
            max(it.first, it.second)
        }
    }

    private fun playDiracDice(gameState: GameState, playerOnesTurn: Boolean): Pair<Long, Long> =
        diracDice.roll().map{ rollWithWeight ->
            (getResultForRoll(gameState.copy(), playerOnesTurn, rollWithWeight.key) * rollWithWeight.value)
        }.sum()


    /**
     * Get the result for a single roll. If this roll leads to a victory, return a pair of (1,0) or (0,1), depending on who won.
     * Else, play another round. and return the results of that round.
     */
    private fun getResultForRoll(gameState: GameState, playerOnesTurn: Boolean, roll: Int): Pair<Long, Long>{
       // println(gameState)
        gameState.addRoll(if (playerOnesTurn) 1 else 2, roll)
        return if (gameState.hasWinner()){
            //println("winning game:\n$gameState")
            if (gameState.playerOneWon()) 1L to 0L else 0L to 1L
        }
        else playDiracDice(gameState, !playerOnesTurn)
    }

    private operator fun Pair<Long, Long>.times(n: Int) = this.first * n to this.second * n
    private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = this.first + other.first to this.second + other.second

    private fun List<Pair<Long, Long>>.sum() = this.reduce { acc, pair -> acc + pair }

}