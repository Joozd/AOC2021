package day21

/**
 * Keep track of a game's state
 */
class GameState(private var p1: Int, private var p2: Int, private val endOfGame: Int = 1000, private var s1: Int = 0, private var s2: Int = 0){
    /**
     * Copy this games state so two separate games can continue from this point
     */
    fun copy() = GameState(p1, p2, endOfGame, s1,s2)

    /**
     * Check if this game has a winner
     */
    fun hasWinner() = listOf(0,1).any { isWinner(it) }

    /**
     * Check if player 1 won.
     * This does not check if there IS a winner, if there isn't it will return 2
     */
    fun playerOneWon(): Boolean = isWinner(0)

    /**
     * Add a dice roll result to this gameState
     */
    fun addRoll(player: Int, value: Int){
        if(player == 1)
            roll1(value)
        else roll2(value)
    }

    // add the roll to player 1
    private fun roll1(value: Int){
        val r = p1 + value
        p1 = if (r == 10) 10 else r%10
        s1 += p1
    }

    //add the roll to player 2
    private fun roll2(value: Int){
        val r = p2 + value
        p2 = if (r == 10) 10 else r%10
        s2 += p2
    }

    /**
     * Get the final score for this game
     */
    fun score(rolls: Int) = (if (playerOneWon()) s1 else s2) * rolls

    /**
     * Check if player 0 or 1 is winner
     */
    private fun isWinner(player: Int): Boolean = if(player == 1) s1 >= endOfGame else s2 >= endOfGame

}