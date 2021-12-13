package common.coordinates

/**
 * because Pairs are too confusing for Joozd
 * Hash is unreliable for x and y values above 2^16
 */

open class Coordinate(val x: Int, val y: Int): Comparable<Coordinate>{
    constructor(coordinatesList: List<Int>): this(coordinatesList[0], coordinatesList[1])

    fun fourNeighbors() = listOf(
        Coordinate(x, y-1),
        Coordinate(x-1, y),
        Coordinate(x+1, y),
        Coordinate(x, y+1)
    )

    fun eightNeighbors() =
        (y-1..y+1).map{ y ->
            (x-1..x+1).mapNotNull { x->
                if(x == this.x && y == this.y) null
                else Coordinate(x,y)
            }
        }.flatten()



    override fun equals(other: Any?) = if (other !is Coordinate) false else
        other.x == x && other.y == y
    override fun toString() = "($x, $y)"

    override fun hashCode(): Int  = x.shl(16) + y

    /**
     *  top to bottom, left to right (y=0 is top row)
     *  so in order:
     *  1,2,3
     *  4,5,6
     *  7,8,9
     */
    override fun compareTo(other: Coordinate): Int = if (y == other.y) x-other.x else y - other.y

    operator fun plus(other: Vector) = Coordinate (x+other.directionX, y+other.directionY)
}