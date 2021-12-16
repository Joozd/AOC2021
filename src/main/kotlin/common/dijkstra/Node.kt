package common.dijkstra

/**
 * A Node to be used in a network for Dijkstra-ing my way to another Node.
 * It is recommended to make a decent HashCode and equals function if these are generated and looked up from there
 */
interface Node<T: Comparable<T>> {
    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node.
     * Nodes can be retrieved in RouteFinder by a nodeRetriever, so we can just generate them.
     */
    fun getNeighbors(): List<Node<T>>

    /**
     * Get the distance to a neighbor.
     * Neighbor will be an actual neighbor, not a generated one.
     */
    fun getDistanceToNeighbor(neighbor: Node<T>): T

    /**
     * Function for adding two distances.
     * Needed because there is no generic "add" function for all types.
     * Usually as simple as:
     * override fun addDistances(d1, d2) = d1 + d2
     */
    fun addDistances(d1: T, d2: T): T

    /**
     * Zero. Usually "0" in the type used.
     */
    val zero: T
}