package day13

import common.Solution
import common.coordinates.Coordinate
import common.coordinates.CoordinateWithValue
import common.coordinates.drawMap
import common.extensions.abs
import common.extensions.grabInts
import common.extensions.words

class Day13: Solution {
    override val day = 13
    private val input = inputLines().filter {it.isNotBlank() }
    private val coordinatesInput = input.filter { it[0].isDigit()}
    private val foldsInput = input.filter { it[0] == 'f'}

    private lateinit var sheet: List<Coordinate>

    /**
     * How many dots are left after one fold?
     * 704
     */
    override fun answer1(): Any {
        sheet = makeDots()
        foldsInput.take(1).forEach { foldingInstruction ->
            sheet = sheet.fold(foldingInstruction)
        }
        return sheet.size
    }

    /**
     * What image is made after all the folds?
     * HGAJBEHC
     */
    override fun answer2(): Any {
        foldsInput.drop(1).forEach { foldingInstruction ->
            sheet = sheet.fold(foldingInstruction)
        }
        return makeMap(sheet)
    }

    /**
     * Make a list of all the dots
     */
    private fun makeDots() =
        coordinatesInput.map{
            Coordinate(it.grabInts())
        }

    /**
     * make a String with all the dots in a map
     */
    private fun makeMap(sheet: List<Coordinate>) =
        sheet.map { CoordinateWithValue(it, FILLED) }.drawMap()

    /**
     * Execute a folding instruction
     */
    private fun List<Coordinate>.fold(foldingInstruction: String) =
        foldingInstruction.words().last().split('=').let {
            foldDotsAlong(it.first()[0], it.last().toInt()).distinct()
        }

    /**
     * Decide if dots are to be folded horizontally or vertically
     */
    private fun List<Coordinate>.foldDotsAlong(axis: Char, line: Int): List<Coordinate> =
        if (axis == 'x') foldHorizontal(line)
        else foldVertical(line)

    /**
     * Fold dots along a horizontal line
     */
    private fun List<Coordinate>.foldHorizontal(line: Int) = map {
        Coordinate(foldOver(it.x,line), it.y)
    }


    /**
     * Fold dots along a vertical line
     */
    private fun List<Coordinate>.foldVertical(line: Int): List<Coordinate> = map {
        Coordinate(it.x, foldOver(it.y, line))
    }

    /**
     * Put [position] the same distance on the other side of [line]
     * Positions on the lower side of the line will stay there.
     */
    private fun foldOver(position: Int, line: Int): Int{
        val difference = (position - line).abs()
        return line - difference
    }

    companion object{
        // The character used to fill the dots on the map
        private const val FILLED = '█'
    }
}