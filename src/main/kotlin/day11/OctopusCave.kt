package day11

import common.coordinates.Coordinate
import common.coordinates.PNGMap
import common.coordinates.drawMap

/**
 * A cave filled with octopuses
 */
class OctopusCave(input: List<String>) {
    // map of coordinates with occupying octopus
    private val oMap = HashMap<Coordinate, Octopus>().apply{
        makeOctopusList(input).forEach{ this[it] = it }
    }
    // list of all octopuses in this cave
    private val octopuses = oMap.values

    /**
     * The amount of octopuses in this cave
     */
    val size get() = octopuses.size

    /**
     * Increase time for this cave by one unit
     */
    fun tick(): Int{
        increaseEnergy()
        flashAll()
        return countAndReset()
    }

    /**
     * Draw a map of this cave
     */
    override fun toString() = octopuses.drawMap()

    fun exportPNG(fileName: String){
        val png = PNGMap(octopuses.toList(), scale = 10){
            makeColor(it.value)
        }
        png.saveImage(fileName)
    }

    private fun makeColor(energy: Int): Int{
        if (energy == 0) return 0xFFFFFF
        val r = 255 * energy / 9
        val g = 255 * (9 - energy) / 9
        return (r.shl(8) + g).shl(8)
    }

    // Increase energy for all octopuses by 1
    private fun increaseEnergy(){
        octopuses.forEach {
            it.energy++
        }
    }

    // Flash all octopuses with sufficient energy, untill none are left to be flashed
    private fun flashAll(){
        while(octopuses.any{it.energy >= FLASH_LEVEL}){
            octopuses.filter { it.energy >= FLASH_LEVEL }.forEach {
                flash(it)
            }
        }
    }

    // Flash an octopus, and set its energy to something extremely negative
    private fun flash(octopus: Octopus){
        octopus.eightNeighbors().forEach {
            oMap[it]?.let { o -> o.energy++ }
        }
        octopus.energy = Int.MIN_VALUE
    }

    // Count all octopuses with negative energy and set them to 0
    private fun countAndReset(): Int{
        val flashedOctopi = octopuses.filter { it.energy < 0}
        flashedOctopi.forEach{
            it.energy = 0
        }
        return flashedOctopi.size

    }

    // Generate a list of all octopuses from [input], used in constructor.
    private fun makeOctopusList(input: List<String>) =
        input.mapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                Octopus(x,y,c.digitToInt())
            }
        }.flatten()

    companion object{
        // The energy level at which an octopus flashes
        private const val FLASH_LEVEL = 10
    }
}