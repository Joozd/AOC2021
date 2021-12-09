package common.coordinates

import common.extensions.abs

class Line(val start: Coordinate, val endInclusive: Coordinate): Iterable<Coordinate> {
    /**
     * true if this is a horizontal line
     */
    val isHorizontal = start.y == endInclusive.y

    /**
     * true if this is a vertical line
     */
    val isVertical = start.x == endInclusive.x

    /**
     * true if this is a diagonal line
     */
    val isDiagonal = (start.x - endInclusive.x).abs() == (start.y - endInclusive.y).abs()

    operator fun contains(element: Coordinate): Boolean = when {
        isHorizontal -> element.x in xRange()
        isVertical -> element.y in yRange()
        else -> false
    }

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<Coordinate> = when{
        isVertical -> verticalIterator()
        isHorizontal -> horizontalIterator()
        isDiagonal -> diagonalIterator()
        else -> error ("Only horizontal, vertical or 45 degree diagonal lines supported")
    }


    //Range of all y coordinates in this line
    private fun yRange() =
        if (endInclusive.y > start.y) start.y..endInclusive.y
        else start.y downTo endInclusive.y

    //Range of all x coordinates in this line
    private fun xRange() =
        if (endInclusive.x > start.x) start.x..endInclusive.x
        else start.x downTo endInclusive.x

        override fun toString(): String = "$start..$endInclusive"

    /**
     * Iterator for a horizontal line
     */
    private fun horizontalIterator() = object: Iterator<Coordinate>{
        private val y = start.y
        private val xRangeIterator = xRange().iterator()

        override fun hasNext(): Boolean =
            xRangeIterator.hasNext()

        override fun next(): Coordinate =
            Coordinate(xRangeIterator.next(), y)
    }

    /**
     * Iterator for a vertical line
     */
    private fun verticalIterator() = object: Iterator<Coordinate>{
        private val x = start.x
        private val yRangeIterator = yRange().iterator()

        override fun hasNext(): Boolean =
            yRangeIterator.hasNext()

        override fun next(): Coordinate =
            Coordinate(x, yRangeIterator.next())
    }

    /**
     * Iterator for a 45 degree diagonal line
     */
    private fun diagonalIterator() = object: Iterator<Coordinate>{
        private val xRangeIterator = xRange().iterator()
        private val yRangeIterator = yRange().iterator()

        override fun hasNext(): Boolean =
            xRangeIterator.hasNext() && yRangeIterator.hasNext()

        override fun next(): Coordinate =
            Coordinate(xRangeIterator.next(), yRangeIterator.next())
    }
}