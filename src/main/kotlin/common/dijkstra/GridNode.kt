package common.dijkstra

/**
 * A node for finding distances in a grid. Distance to neighbor is always 0
 */
interface GridNode: Node<Int> {
    val x: Int
    val y: Int
}