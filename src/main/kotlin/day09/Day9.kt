package day09

import common.coordinates.Coordinate
import common.Solution
import common.coordinates.CoordinateWithValue
import common.coordinates.PNGMap
import common.extensions.product
import common.extensions.removeAllKeys

class Day9: Solution {
    override val day = 9
    private val input = inputLines()
    private lateinit var map: Map<Coordinate, Int>

    /**
     * Create a Map<Coordinate, Int> from input
     */
    override fun prepare() {
        map = input.mapIndexed { y, line ->
            line.mapIndexed { x, elevation ->
                Coordinate(x,y) to elevation.digitToInt()
            }
        }.flatten().toMap()
    }

    /**
     * Find the points for which all neighbors have a higher value
     */
    override fun answer1(): Any {
        val localMinimums = map.keys.filter { map[it]!! < it.getLowestNeighbor() }
        return localMinimums.sumOf{ map[it]!! + 1 }
    }

    /**
     * Find the three largest basins
     */
    override fun answer2(): Any {
        val basins = makeBasins()

        /* This part will output 2 PNG maps ******
        exportMap()                             //
        exportBasins(basins)                    //
        *****************************************/

        return getProductOfBiggestThree(basins)
    }

    /**
     * Get the size of the biggest three basins and multiply them
     */
    private fun getProductOfBiggestThree(basins: List<List<Coordinate>>) =
        basins.map { it.size }.sortedDescending().take(3).product()

    /**
     * Get the neighbour with the lowest value
     */
    private fun Coordinate.getLowestNeighbor() =
        possibleNeighbors().minOf { map[it] ?: Int.MAX_VALUE }

    /**
     * Make a list of basins from a map of coordinates and altitudes
     */
    private fun makeBasins(): List<List<Coordinate>>{
        val foundBasins = ArrayList<List<Coordinate>>()
        //remove all parts that are not part of a basin
        val basinsMap: MutableMap<Coordinate, Int> = makeBasinsMap()

        while(basinsMap.isNotEmpty()) {
            val foundBasin = findBasin(basinsMap)
            foundBasins.add(foundBasin)
            basinsMap.removeAllKeys(foundBasin)
        }
        return foundBasins
    }

    /**
     * Make a map with only basins, any separations (value 9) between basins are not in this map.
     */
    private fun makeBasinsMap() = map.filter { it.value != 9 }.toMutableMap()

    /**
     * Find all connecting Coordinates in[map]
     * Map has all "9" values removed so all connecting coordinates are part of the same basin.
     */
    private fun findBasin(map: Map<Coordinate, Int>): List<Coordinate> {
        val foundPoints = ArrayList<Coordinate>()
        foundPoints.add(map.keys.first())
        var foundNeighbors = findAllNeighbors(foundPoints, map)

        // While any point in [foundPoints] has any neighbors that are not in the list yet:
        // add those neighbors to the list
        while(foundNeighbors.isNotEmpty()){
            foundPoints.addAll(foundNeighbors)
            foundNeighbors = findAllNeighbors(foundPoints, map)
        }
        return foundPoints
    }

    /**
     * Find all neighbors from a list of Coordinates that are valid coordinates in [map]
     */
    @Suppress("ConvertCallChainIntoSequence") // it is actually about 20% slower as a sequence
    private fun findAllNeighbors(coordinates: List<Coordinate>, map: Map<Coordinate, Int>): List<Coordinate> =
        coordinates.map { it.possibleNeighbors() }
            .flatten()
            .distinct()
            .filter { it in map.keys }
            .filter {it !in coordinates }


    /**
     * Bonus: Export some PNG files with the map
     */

    /**
     * Export map (red = high, green is low)
     * Could probably extract some functions for extra readability but meh
     */
    private fun exportMap(){
        val outputFile = "c:\\temp\\map.png"
        val data = map.keys.map { CoordinateWithValue(it.x, it.y, map[it]!!) }
        PNGMap(data).apply{
            defineColors{ point ->
                val r = 255 * point.value / 9
                val g = 255 * (9 - point.value) / 9
                (r.shl(8) + g).shl(8)
            }
            saveImage(outputFile)
        }
    }


    /**
     * Export all different basins with random colors.
     * Could probably extract some functions for extra readability but meh
     */
    private fun exportBasins(basins: List<List<Coordinate>>){
        val outputFile = "c:\\temp\\basins.png"
        val codedBasins = basins.mapIndexed { index, coordinates ->
            coordinates.map { CoordinateWithValue(it.x, it.y, index) }
        }.flatten()
        val colorsMap = codedBasins.indices.map { it to (0..0xFFFFFF).random() }.toMap()
        PNGMap(codedBasins).apply{
            defineColors{ v-> colorsMap[v.value] ?: 0xFF0000 }
            saveImage(outputFile)
        }
    }
}