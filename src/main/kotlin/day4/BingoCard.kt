package day4

import common.extensions.replace
import common.extensions.sumNotNull
import common.extensions.words

/**
 * a Bingo Card
 */
class BingoCard private constructor(private val rows: List<MutableList<Int?>>) {
    private val boxes get() = rows.flatten()                                      // all boxes in one list
    private val columns get() = rows.indices.map { i -> rows.map { it[i] } }      // all columns on this BingoCard

    /**
     * Mark a box if it holds this value.
     * @return the score of this card when it has a full line, or null iff it doesn't
     */
    fun mark(drawnNumber: Int){
        rows.forEach { it.replace(drawnNumber, null) }
    }

    /*********************
     * Private functions *
     *********************/

    /**
     * Get score from this card: (<Unmarked numbers> * [drawnNumber])
     * If no full line on this card, returns null
     */
    fun score(drawnNumber: Int): Int? =
        if (hasAFullLine()) (boxes.sumNotNull() * drawnNumber)
        else null

    /**
     * @return true if this card has a full line (row or column), false if not.
     */
    private fun hasAFullLine() = (rows + columns).any { v -> v.sumNotNull() == 0 }

    companion object{
        /**
         * Build a [BingoCard] from a String
         */
        fun ofCardString(cardString: String): BingoCard{
            return BingoCard(
                cardString.lines().map{ line ->
                    line.words().map{ it.toInt() }.toMutableList()
                }
            )
        }
    }
}