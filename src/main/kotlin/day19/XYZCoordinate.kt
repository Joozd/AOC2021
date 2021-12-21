package day19

import common.extensions.grabInts

data class XYZCoordinate(val x: Int, val y: Int, val z: Int){
    override fun toString() = "($x,$y,$z)"

    fun distanceTo(other: XYZCoordinate): Int{
        val xDif = maxOf(x, other.x) - minOf(x, other.x)
        val yDif = maxOf(y, other.y) - minOf(y, other.y)
        val zDif = maxOf(z, other.z) - minOf(z, other.z)

        return xDif + yDif + zDif
    }

    companion object{
        fun parse(line: String): XYZCoordinate =
            line.grabInts().let{ i ->
                XYZCoordinate(i[0], i[1], i[2])
            }
    }
}