package common.coordinates

import day22.ReactorCube

open class Cube(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    override fun toString() = "(x=$xRange,y=$yRange,z=$zRange)"
    operator fun contains(xyzCoordinate: XYZCoordinate): Boolean =
        xyzCoordinate.x in xRange && xyzCoordinate.y in yRange && xyzCoordinate.z in zRange

    fun overlaps(other: ReactorCube) =
        xRange.overlaps(other.xRange) && yRange.overlaps(other.yRange) && zRange.overlaps(other.zRange)

    private fun IntRange.overlaps(other: IntRange) = this.first <= other.last && this.last >= other.first

    /**
     * Check if a surface intersects with this cube
     */
    fun containsX(x: Int) = x in xRange
    fun containsY(y: Int) = y in yRange
    fun containsZ(z: Int) = z in zRange

    fun volume(): Long = xRange.length().toLong() * yRange.length() * zRange.length()

    fun copy(xRange: IntRange = this.xRange, yRange: IntRange = this.yRange, zRange: IntRange = this.zRange)=
        Cube(xRange, yRange, zRange)

    /**
     * Divide this cube into suCubes over an intersecting surface defined by its x, y or z coordinate.
     * The dividing surface is included in the cube with the highest x, y or z values.
     * @return a list of the two cubes made by splitting this one, or of this cube if surface outside this
     */
    fun splitX(xSurface: Int): List<Cube>{
        if (xSurface !in xRange) return listOf(this)
        return listOf(this.copy(xRange = xRange.first until xSurface), this.copy(xRange = xSurface..xRange.last))
    }

    fun splitY(ySurface: Int): List<Cube>{
        if (ySurface !in yRange) return listOf(this)
        return listOf(this.copy(yRange = yRange.first until ySurface), this.copy(yRange = ySurface..yRange.last))
    }

    fun splitZ(zSurface: Int): List<Cube>{
        if (zSurface !in zRange) return listOf(this)
        return listOf(this.copy(zRange = zRange.first until zSurface), this.copy(zRange = zSurface..zRange.last))
    }

    private fun IntRange.length(): Int = last - first + 1
}