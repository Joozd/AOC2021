package day15

import common.coordinates.CoordinateWithValue
import common.dijkstra.IntNode
import common.dijkstra.Node

class Cave(
    x: Int,
    y: Int,
    value: Int = -1,
    var riskToHere: Int = Int.MAX_VALUE,
    var done: Boolean = false
): CoordinateWithValue<Int>(x,y,value), IntNode {
    var previousCave: Cave? = null
    override fun toString() = "($x,$y)"
    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node
     * Nodes will be retrieved from map in RouteFinder by nodeRetriever so can just generate them.
     */
    override fun getNeighbors(): List<Cave> = fourNeighbors().map { Cave(it.x,it.y)}

    /**
     * Get the distance to a neighbor
     */
    override fun getDistanceToNeighbor(neighbor: Node<Int>): Int = (neighbor as Cave).value

    fun setRisk(from: Cave){
        val newRisk = from.riskToHere + value
        riskToHere = minOf (riskToHere, newRisk)
        if (newRisk == riskToHere) previousCave = from
    }

    fun setRisk(forceValue: Int){
        riskToHere = forceValue
    }
}