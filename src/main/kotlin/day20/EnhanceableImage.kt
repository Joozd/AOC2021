package day20

import common.BinaryNumber
import common.coordinates.Coordinate
import common.coordinates.CoordinateWithValue

class EnhanceableImage private constructor(private var map: Map<Coordinate, CoordinateWithValue<Char>>, private val algorithm: String) {
    private fun maxX() = map.keys.maxOf{it.x}
    private fun maxY() = map.keys.maxOf{it.y}
    private fun minX() = map.keys.minOf{it.x}
    private fun minY() = map.keys.minOf{it.y}
    private var outsideColor = BLACK

    fun coordinates() = map.values.toList()

    /**
     * Enhance this EnhanceableImage
     */
    fun enhance(){
        val expandedMap = expandMap()
        //println("expanded map:\n${printMap(expandedMap)}\nxxx")
        map = enhanceMap(expandedMap)
        updateOutsideColor()
    }

    fun countLitPixels() = map.values.count{ it.value == WHITE }

    // make lines before and after and top and bottom
    private fun expandMap(): Map<Coordinate, CoordinateWithValue<Char>>{
        val xRange = minX()..maxX()
        val yRange = minY()..maxY()
        val newLines = makeLines(xRange, yRange.first-1, yRange.last + 1)
        val newColumns = makeColumns(yRange, xRange.first-1, xRange.last + 1)
        val newCorners = makeCorners(xRange, yRange)
        val newMap = (newLines + newColumns +newCorners).associateWith { it }
        return map + newMap
    }

    /**
     * Make lines of coordinates (to increase size of map)
     */
    private fun makeLines(range: IntRange, vararg columns: Int): List<CoordinateWithValue<Char>> =
        columns.map { y ->
            (range.first - 1..range.last + 1).map { x -> CoordinateWithValue(x, y, outsideColor) }
        }.flatten()

    /**
     * Make columns of coordinates (to increase size of map)
     */
    private fun makeColumns(range: IntRange, vararg lines: Int): List<CoordinateWithValue<Char>> =
        lines.map { x ->
            (range.first - 1..range.last + 1).map { y -> CoordinateWithValue(x, y, outsideColor) }
        }.flatten()

    /**
     * Make corners (to increase size of map)
     */
    private fun makeCorners(xRange: IntRange, yRange: IntRange): List<CoordinateWithValue<Char>> =
        listOf(xRange.first -1, xRange.last + 1).map{ x->
            listOf(yRange.first - 1, yRange.last + 1).map{ y -> CoordinateWithValue(x,y,outsideColor) }
        }.flatten()

    private fun enhanceMap(
        map: Map<Coordinate, CoordinateWithValue<Char>>
    ): Map<Coordinate, CoordinateWithValue<Char>> {
        val newMap: Map<Coordinate, CoordinateWithValue<Char>> = map.values.map{ cwv ->
            cwv.lookupNewValue(map).let{ it to it }
        }.toMap()
        return newMap
    }

    /**
     * Lookup new value for a coordinate
     */
    private fun CoordinateWithValue<Char>.lookupNewValue(
        map: Map<Coordinate, CoordinateWithValue<Char>>
    ): CoordinateWithValue<Char> {
        val index = BinaryNumber(block(1).map{ (map[it]?.value ?: outsideColor) == WHITE }).toInt()
        return CoordinateWithValue(this, algorithm[index])
    }

    /**
     * flip [outsideColor] if blocks of solid white or black lead to the opposite color
     */
    private fun updateOutsideColor(){
        if (outsideColor == BLACK && algorithm[0] == BLACK) return
        if (outsideColor == WHITE && algorithm.last() == WHITE) return
        outsideColor = if (outsideColor == '#') '.' else '#'
    }


    companion object {
        /**
         * Make an EnhanceableImage from an input String
         */
        fun make(inputLines: List<String>): EnhanceableImage {
            val algorithm = inputLines[0]
            val initialImage = inputLines.drop(2)

            val map = HashMap<Coordinate, CoordinateWithValue<Char>>()

            initialImage.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    CoordinateWithValue(x, y, c).let {
                        map[it] = it
                    }
                }
            }
            return EnhanceableImage(map, algorithm)
        }

        private const val WHITE = '#'
        private const val BLACK = '.'
    }
}