package day22

import common.Solution

class Day22: Solution {
    override val day = 22
    private val input = inputLines()
    private val cubes = ArrayList<ReactorCube>()

    /**
     * Add all cubes inside (-50..50) to the list, then get amount of switched-on lights
     */
    override fun answer1(): Any {
        input.forEach { line ->
            ReactorCube.parse(line){ it in -50..50 }?.let{ cube ->            // make a cube if it is in (-50..50)
                includeCubeInList(cube)
            }
        }
        //This also works with calculateVolumeClean() of course, but this is the way I initially did it
        //For these small cubes with so many intersecting lines it's just a few ms difference.
        return cubes.filter { it.switchOn }.sumOf{
            it.calculateVolumeDirty()
        }
    }

    /**
     * Add all cubes outside (-50..50) to the list, then get amount of switched-on lights
     */
    override fun answer2(): Any {
        input.forEach { line ->
            ReactorCube.parse(line) { it !in -50..50}?.let { cube -> // make a cube if it is NOT in (-50..50)
                includeCubeInList(cube)
            }
        }
        return cubes.filter { it.switchOn }.sumOf { cube ->
            cube.calculateVolumeClean()
        }
    }

    /**
     * Overlap this cube with all currently known cubes, then add it to the list of known cubes.
     */
    private fun includeCubeInList(cube: ReactorCube){
        overlapCube(cube)
        cubes.add(cube)
    }

    /**
     * Overlap [cube] to all cubes in [cubes]
     */
    private fun overlapCube(cube: ReactorCube) {
        cubes.forEach { knownCube -> knownCube.overlapBy(cube) }
    }
}