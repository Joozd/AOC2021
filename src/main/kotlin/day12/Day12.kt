package day12

import common.Solution

class Day12: Solution {
    override val day = 12

    private val input = inputLines()
    private lateinit var cavesMap: Map<String, Cave>

    /**
     * Prepare the caves map
     */
    override fun prepare(){
        val map = LinkedHashMap<String, Cave>()

        // put all caves in map
        listAllCavesInInput().forEach { map[it] = Cave(it) }

        //add all exits to caves
        input.forEach{ l ->
            addExitsToCaves(l, map)
        }
        cavesMap = map
    }

    /**
     * Find all routes through the caves
     */
    override fun answer1(): Any {
        val allRoutes = findAllRoutes()
        return allRoutes.size
    }

    /**
     * Find all routes through the caves with a single double visit to a small cave
     */
    override fun answer2(): Any {
        val allRoutes = findAllRoutes(allowDoubleSmallCave = true)
        return allRoutes.size
    }


    //used in prepare()
    private fun listAllCavesInInput() =
        input.map { it.split('-') }.flatten()

    //used in prepare()
    private fun addExitsToCaves(l: String, map: LinkedHashMap<String, Cave>) {
        val cavesInLine = l.split('-')
        map[cavesInLine[0]]!!.addExit(cavesInLine[1])
        map[cavesInLine[1]]!!.addExit(cavesInLine[0])
    }

    // Find all routes from START to END
    private fun findAllRoutes(allowDoubleSmallCave: Boolean = false): List<List<String>>{
        var currentRoutes = listOf(listOf(START))
        while(currentRoutes.any { isIncompleteRoute(it)}){
            currentRoutes = addAllPossibleExits(currentRoutes, allowDoubleSmallCave)
        }
        return currentRoutes
    }

    // Add all possible exits to all routes in [routes] and return a list of all routes that can make
    private fun addAllPossibleExits(routes: List<List<String>>, allowDoubleSmallCave: Boolean) = routes.map{ route ->
        if (isIncompleteRoute(route))
            getExits(route.last())
                .filter { exit -> isValidExit(exit, route, allowDoubleSmallCave) }
                .map{ exit -> route + exit }
        else listOf(route)
    }.flatten()

    // true if this [route] does not end with END
    private fun isIncompleteRoute(route: List<String>) = route.last() != END

    // get all exits for a [cave]
    private fun getExits(cave: String) = cavesMap[cave]!!.exits

    // true if this [exit] is valid for this [route]
    private fun isValidExit(exit: String, route: List<String>, allowDoubleSmallCave: Boolean): Boolean =
            exit.first().isUpperCase() || exit !in route || (allowDoubleSmallCave && noDoubleSmallCavesIn(route))

    // true if no small cave has been visited twice in this route
    private fun noDoubleSmallCavesIn(route: List<String>) =
        route.filter {it.first().isLowerCase()}.let{ smallCaves ->
            smallCaves.size == smallCaves.toSet().size
        }

    companion object {
        private const val START = "start"
        private const val END = "end"
    }
}