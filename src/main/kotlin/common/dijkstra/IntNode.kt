package common.dijkstra

interface IntNode: Node<Int> {
    override fun addDistances(d1: Int, d2: Int): Int =  d1 + d2

    override val zero get() = 0
}