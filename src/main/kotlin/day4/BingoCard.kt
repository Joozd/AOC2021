package day4

import common.extensions.words

/**
 * a Bingo Card
 */
class BingoCard private constructor(private val rows: List<List<Box>>) {
    private val boxes = rows.flatten()                                      // all boxes in one list
    private val columns = rows.indices.map { i -> rows.map { it[i] } }      // all columns on this BingoCard

    /**
     * Mark a box if it holds this value.
     * @return the score of this card when it has a full line, or null iff it doesn't
     */
    fun mark(drawnNumber: Int): Int?{
        boxes.forEach{ box -> box.mark(drawnNumber) }
        return score(drawnNumber)
    }

    /*********************
     * Private functions *
     *********************/

    /**
     * Get score from this card: (<Unmarked numbers> * [drawnNumber])
     * If no full line on this card, returns null
     */
    private fun score(drawnNumber: Int): Int? =
        if (hasAFullLine()) (boxes.filter { !it.marked }.sumOf{ it.value } * drawnNumber)
        else null

    /**
     * @return true if this card has a full line (row or column), false if not.
     */
    private fun hasAFullLine() = (rows + columns).any { v -> v.all { it.marked } }

    /**
     * A [Box] is a position on a [BingoCard] that holds one number.
     * It can either be marked or not marked.
     */
    private class Box(val value: Int){
        var marked: Boolean = false
            private set

        /**
         * Mark this Box as "marked"
         */
        fun mark(drawnNumber: Int){
            if (value == drawnNumber) marked = true
        }
    }

    companion object{
        /**
         * Build a [BingoCard] from a String
         */
        fun ofCardString(cardString: String): BingoCard{
            return BingoCard(
                cardString.lines().map{ line ->
                    line.words().map{ Box(it.toInt()) }
                }
            )
        }
    }
}