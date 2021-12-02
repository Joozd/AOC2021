package common

/**
 * because Pairs are too confusing for Joozd
 * Hash is unreliable for x and y values above 2^16
 */

open class Coordinate(val x: Int, val y: Int): Comparable<Coordinate>{
    override fun equals(other: Any?) = if (other !is Coordinate) false else
        other.x == x && other.y == y
    override fun toString() = "Coordinate ($x, $y)"

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