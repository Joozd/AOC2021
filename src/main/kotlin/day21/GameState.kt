package day21

class GameState(private var p1: Int, private var p2: Int, private val endOfGame: Int = 1000, private var s1: Int = 0, private var s2: Int = 0){
    fun copy() = GameState(p1, p2, endOfGame, s1,s2)
    override fun toString() = "p1, s1 / p2, s2"
    fun hasWinner() = listOf(0,1).any { isWinner(it) }

    /**
     * This does not check if there IS a winner, if there isn't it will return 2
     */
    fun playerOneWon(): Boolean = isWinner(0)

    fun addRoll(player: Int, value: Int){
        if(player == 1)
            roll1(value)
        else roll2(value)
    }
    private fun roll1(value: Int){
        val r = p1 + value
        p1 = if (r == 10) 10 else r%10
        s1 += p1
    }
    private fun roll2(value: Int){
        val r = p2 + value
        p2 = if (r == 10) 10 else r%10
        s2 += p2
    }

    fun score(rolls: Int) = (if (isWinner(0)) s1 else s2) * rolls

    private fun isWinner(player: Int): Boolean = if(player == 1) s1 >= endOfGame else s2 >= endOfGame

}