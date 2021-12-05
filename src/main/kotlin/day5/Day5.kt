package day5

import common.Coordinate
import common.Line
import common.Solution
import common.extensions.countDuplicates
import common.extensions.grabInts
import common.extensions.words

class Day5: Solution {
    override val day = 5

    private val input = inputLines()

    private lateinit var lines: List<Line>

    override fun prepare() {
        lines = input.map { buildLine(it) }
    }

    override fun answer1(): Any {
        val relevantLines = lines.filter { !it.isDiagonal }
        val allCoordinatesOnLines = relevantLines.flatten()
        return allCoordinatesOnLines.countDuplicates()
    }

    override fun answer2(): Any {
        val allCoordinatesOnLines = lines.flatten()
        return allCoordinatesOnLines.countDuplicates()
    }

    /**
     * Build a [Line] from a String like "0,9 -> 5,9"
     */
    private fun buildLine(lineString: String): Line{
        val words = lineString.words()
        val start = coordinateFromString(words.first())
        val end = coordinateFromString(words.last())
        return Line(start, end)
    }

    /**
     * Build a [Coordinate] from a string like "0,9"
     */
    private fun coordinateFromString(string: String) =
        string.grabInts().let { Coordinate(it[0], it[1])}
}