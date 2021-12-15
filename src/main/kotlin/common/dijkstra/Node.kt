package common.dijkstra

/**
 * A Node to be used in a network for Dijkstra-ing my way to another Node.
 */
interface Node<T> where T: Number, T: Comparable<T> {
    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node
     */
    fun getNeighbors(): List<Node<T>>

    /**
     * Get the distance to a neighbor
     */
    fun getDistanceToNeighbor(neighbor: Node<T>): T
}