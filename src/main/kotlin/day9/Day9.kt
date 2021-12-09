package day9

import common.Coordinate
import common.Solution
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
        map = input.mapIndexed { y, s ->
            s.mapIndexed { x, c ->
                Coordinate(x,y) to c.toString().toInt()
            }
        }.flatten().toMap()
    }

    /**
     * Find the points for which all neighbors have a higher value
     */
    override fun answer1(): Any {
        val localMinimums = map.keys.filter { map[it]!! < it.getLowestNeighbor() }
        return localMinimums.sumBy { map[it]!! + 1 }
    }

    /**
     * Find the three largest basins
     */
    override fun answer2(): Any {
        val basins = makeBasins()
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
        //remove all parts that are not part of a basin
        val basinsMap = map.filter { it.value != 9 }.toMutableMap()
        val foundBasins = ArrayList<List<Coordinate>>()
        while(basinsMap.isNotEmpty()) {
            val foundBasin = findBasin(basinsMap)
            foundBasins.add(foundBasin)
            basinsMap.removeAllKeys(foundBasin)
        }
        return foundBasins
    }

    /**
     * Find all connecting Coordinates in[map]
     * Map has all "9" values removed so all connecting coordinates are part of the same basin.
     */
    private fun findBasin(map: Map<Coordinate, Int>): List<Coordinate> {
        val foundPoints = ArrayList<Coordinate>()
        foundPoints.add(map.keys.first())
        var foundNeighbours = findAllNeighbors(foundPoints, map)
        while(foundNeighbours.isNotEmpty()){
            foundPoints.addAll(foundNeighbours)
            foundNeighbours = findAllNeighbors(foundPoints, map)
        }
        return foundPoints
    }

    /**
     * Find all neighbors from a list of Coordinates that are valid coordinates in [map]
     */
    private fun findAllNeighbors(coordinates: List<Coordinate>, map: Map<Coordinate, Int>): List<Coordinate> =
        coordinates.map { it.possibleNeighbors() }
            .flatten()
            .distinct()
            .filter { it in map.keys }
            .filter {it !in coordinates }
}