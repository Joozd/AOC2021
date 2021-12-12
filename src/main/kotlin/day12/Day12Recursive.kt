package day12

import common.Solution

/**
 * Recursive (DFS) version of day 12
 * Did this when fully awake, about 4 times as fast as BFS version
 */
class Day12Recursive: Solution {
    override val day = 12
    private val input = inputLines()
    private lateinit var cavesMap: Map<String, Cave>

    /**
     * Prepare the caves map
     */
    override fun prepare() {
        val map = LinkedHashMap<String, Cave>()

        // put all caves in map
        listAllCavesInInput().forEach { map[it] = Cave(it) }

        //add all exits to caves
        input.forEach { l ->
            addExitsToCaves(l, map)
        }
        cavesMap = map
    }

    override fun answer1(): Any = findAmountOfRoutesFromHere(listOf(START))

    override fun answer2(): Any = findAmountOfRoutesFromHere(listOf(START), allowDouble = true)

    /**
     * Find amount of valid routes to exit for a previous route [routeToHere]
     * - If [routeToHere] is a route ending at [END], this has 1 valid route
     * - If [routeToHere] ends with a room with exits, it will return the sum of all routes from all those exits
     * - If [routeToHere] ends with a room without any valid exits, it will return the sum of an empty list (== 0)
     */
    private fun findAmountOfRoutesFromHere(routeToHere: List<String>, allowDouble: Boolean = false): Int {
        val here = routeToHere.last()
        if (here == END) return 1
        val exitsFromHere = getValidExits(cavesMap[here]!!.exits, routeToHere, allowDouble)
        return exitsFromHere.sumOf { exit ->
            findAmountOfRoutesFromHere(
                routeToHere = routeToHere + exit,
                allowDouble = allowDouble && exit.isNotASmallCaveAlreadyVisited(routeToHere)
            )
        }
    }

    // Get valid exits for a cave
    private fun getValidExits(exits: List<String>, routeToHere: List<String>, allowDouble: Boolean) =
        if (allowDouble)
            exits.filter { it != START }
        else
            exits.filter { it!= START && it.isNotASmallCaveAlreadyVisited(routeToHere)}


    //used in prepare()
    private fun listAllCavesInInput() =
        input.map { it.split('-') }.flatten()

    //used in prepare()
    private fun addExitsToCaves(l: String, map: LinkedHashMap<String, Cave>) {
        val cavesInLine = l.split('-')
        map[cavesInLine[0]]!!.addExit(cavesInLine[1])
        map[cavesInLine[1]]!!.addExit(cavesInLine[0])
    }

    /**
     * True if this is a large or an unvisited cave or both
     * False if this is a small cave that has already been visited.
     */
    private fun String.isNotASmallCaveAlreadyVisited(route: List<String>) =
        this[0].isUpperCase() || this !in route

    companion object {
        private const val START = "start"
        private const val END = "end"
    }
}