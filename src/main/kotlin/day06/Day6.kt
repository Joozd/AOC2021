package day06

import common.Solution
import common.extensions.grabInts

class Day6: Solution {
    override val day = 6
    private val input = readInput()

    /**
     * Spawn and tick the list like in the example
     */
    override fun answer1(): Any {
        var fishes = input.grabInts()
        repeat(DAYS_QUESTION_1){
            fishes = fishes.spawn()
            fishes = fishes.tick()
        }
        return fishes.size
    }

    /**
     * Use a more sensible approach using [FishReproducer]
     */
    override fun answer2(): Any {
        val fishReproducer = FishReproducer(input.grabInts())
        repeat(DAYS_QUESTION_2){
            fishReproducer.tick()
        }
        return fishReproducer.count()
    }

    private fun List<Int>.spawn(): List<Int>{
        val newFish = count { it == 0 }
        return this.updateSpawnTimes() + MutableList(newFish) { NEW_FISH_TIMER }
    }

    private fun List<Int>.tick() = map { it -1 }

    /**
     * Set all values '0' to 'RESET_TIMER'
     */
    private fun List<Int>.updateSpawnTimes() = this.map {if (it == 0) RESET_TIMER else it }

    companion object{
        private const val NEW_FISH_TIMER = 9 // will be reduced by one during this tick
        private const val RESET_TIMER  = 7
        private const val DAYS_QUESTION_1 = 80
        private const val DAYS_QUESTION_2 = 256
    }
}

private val testInput = "3,4,3,1,2"
