package day15

import common.Solution
import common.coordinates.PNGMap
import common.dijkstra.Dijkstra

class Day15: Solution {
    override val day = 15
    private val input = inputLines()

    override fun answer1(): Any? {
        val cavesMap = makeCavesList(input).associateBy { it }
        return findDistanceStartToEnd(cavesMap)
    }

    override fun answer2(): Any? {
        val caves = input.timesFiveIncreased()
        val cavesMap = makeCavesList(caves).associateBy { it }
        return findDistanceStartToEnd(cavesMap)
    }

    private fun findDistanceStartToEnd(cavesMap: Map<Cave, Cave>): Int? {
        val routeFinder = Dijkstra<Int>(cavesMap.size) { cavesMap[it] }
        val start = startCave(cavesMap)
        val end = endCave(cavesMap)
        val route = routeFinder.findRoute(start, end)!!.map { it as Cave}
        // PNGMap(route, scale = 3).saveImage("c:\\temp\\caves.png")
        return routeFinder.getFoundDistance()
    }

    private fun makeCavesList(input: List<String>) =
        input.mapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                Cave(x, y, c.digitToInt())
            }
        }.flatten()

    private fun startCave(cavesMap: Map<Cave, Cave>) = cavesMap.keys.minOrNull()!!
    private fun endCave(cavesMap: Map<Cave, Cave>) = cavesMap.keys.maxOrNull()!!
}