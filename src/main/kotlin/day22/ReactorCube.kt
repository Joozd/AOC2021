package day22

import common.coordinates.Cube
import common.extensions.words
import common.coordinates.XYZCoordinate
import common.extensions.grabInts

/**
 * A ReactorCube is used in powering up a submarine reactor
 */
class ReactorCube(xRange: IntRange, yRange: IntRange, zRange: IntRange,val switchOn: Boolean): Cube(xRange, yRange, zRange) {
    private val overlappingCubes = ArrayList<ReactorCube>()

    /**
     * Check if two cubes overlap
     */
    fun overlapBy(overridingCube: ReactorCube){
        if (overridingCube.overlaps(this)){
            overlappingCubes.add(overridingCube)
        }
    }

    /**
     * Calculate volume of this cube without overlaps by just looking at every pixel and checking if it is overlapped.
     */
    fun calculateVolumeDirty(): Long{
        var result: Long = 0
        xRange.forEach{ x ->
            yRange.forEach { y ->
                zRange.forEach { z ->
                    val coordinate = XYZCoordinate(x,y,z)
                    if (overlappingCubes.none{ coordinate in it})
                        result++
                }
            }
        }
        return result
    }

    /**
     * Calculate the volume of this cube that is not overlapped by any other cubes.
     * This is done by dividing the cube into subCubes for every intersecting cube (see [makeSubCubes])
     * Then, every subCube is either completely overlapped or not overlapped, in which case its volume is counted.
     * The sum of all those counted cubes is the volume of un-overlapped space in this cube.
     */
    fun calculateVolumeClean(): Long {
        val subCubes = makeSubCubes()
        return getVolumeOfNonOverlappingCubes(subCubes)
    }

    /**
     * For every cube in [subCubes], check if it overlaps with any cube in [overlappingCubes].
     * If it isn't, get its volume.
     * @return the sum of all non-overlapping subCubes.
     */
    private fun getVolumeOfNonOverlappingCubes(subCubes: List<Cube>): Long {
        var totalVolume = 0L
        subCubes.forEach { subCube ->
            if (overlappingCubes.none { subCube.overlaps(it) })
                totalVolume += subCube.volume()
        }
        return totalVolume
    }

    /**
     * Make subCubes from this cube.
     * This is done by looking at every intersecting surface from all [overlappingCubes], and splitting all
     * currently known subCubes along that surface.
     */
    private fun makeSubCubes(): List<Cube>{
        var allCubes: List<Cube> = listOf(this)

        /*
         * make all surfaces that are at the surface of an intersecting cube.
         * range.last is +1 because lower (on x, y or z axis) cube will be cut off before
         *  last in [Cube] but it is a part of it
         * (see Cube.SplitX/Y/Z)
         */
        val xSurfaces = (overlappingCubes.map{ it.xRange.first} + overlappingCubes.map { it.xRange.last + 1 })
            .filter { this.containsX(it) }
        val ySurfaces = (overlappingCubes.map{ it.yRange.first} + overlappingCubes.map { it.yRange.last + 1 })
            .filter { this.containsY(it) }
        val zSurfaces = (overlappingCubes.map{ it.zRange.first} + overlappingCubes.map { it.zRange.last + 1 })
            .filter { this.containsZ(it) }
        //make all cubes that are separated by those surfaces

        xSurfaces.forEach{ xSurface ->
            allCubes = allCubes.map{it.splitX(xSurface)}.flatten()
        }
        ySurfaces.forEach{ ySurface ->
            allCubes = allCubes.map{it.splitY(ySurface)}.flatten()
        }
        zSurfaces.forEach{ zSurface ->
            allCubes = allCubes.map{it.splitZ(zSurface)}.flatten()
        }
        return allCubes
    }

    companion object{
        /**
         * Parse an input line into a ReactorCube object
         * Will only parse if [filter] is fulfilled, otherwise it will return null
         */
        fun parse(line: String, filter: (Int) -> Boolean): ReactorCube?{
            val ll = line.grabInts()
            return if (filter(ll[0])) {
                val switchOn = line.words().first() == "on"
                return ReactorCube(ll[0]..ll[1], ll[2]..ll[3], ll[4]..ll[5], switchOn)
            } else null
        }
    }
}