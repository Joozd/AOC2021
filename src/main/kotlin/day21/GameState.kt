package day21

class GameState(initialPositions: List<Int>, private val endOfGame: Int = 1000, private val s: IntArray = IntArray(3) { 0 }){
    fun copy() = GameState(p.drop(1), endOfGame, s.copyOf())
    override fun toString() = "s[1] = ${s[1]}, p[1] = ${p[1]}\ns[2] = ${s[2]}, p[2] = ${p[2]}\n"
    private val p = IntArray(3){ if (it == 0) 0  else initialPositions[it-1] } // extra field because we use indices 1 and 2
    fun hasWinner() = listOf(1,2).any { isWinner(it) }

    /**
     * This does not check if there IS a winner, if there isn't it will return 2
     */
    fun playerOneWon(): Boolean = isWinner(1)

    fun addRoll(player: Int, value: Int){
        val r = p[player] + value
        p[player] = if (r == 10) 10 else r%10
        s[player] += p[player]
        //println(this)
    }

    fun score(rolls: Int) = (if (isWinner(1)) s[2] else s[1]) * rolls

    private fun isWinner(player: Int): Boolean = s[player] >= endOfGame

}