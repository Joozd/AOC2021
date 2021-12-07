package day7

import common.Solution
import common.extensions.abs
import common.extensions.grabInts

class Day7: Solution {
    override val day = 7

    private val crabs = readInput().grabInts()
    private val maxPosition = crabs.maxOrNull() ?: error ("error 1")
    private val possibleTargets = (0..maxPosition)


    override fun answer1(): Any =
        possibleTargets.minOf { t ->
            crabs.distancesFromTarget(t).sum()
        }

    override fun answer2(): Any =
        possibleTargets.minOf { t ->
            crabs.incrementingFuelBurnDistancesToTarget(t).sum()
        }


    private fun List<Int>.distancesFromTarget(target: Int) =
        map { (it - target).abs()}

    private fun List<Int>.incrementingFuelBurnDistancesToTarget(target: Int) =
        map { getIncrementedFuelUse((it-target).abs()) }

    private fun getIncrementedFuelUse(steps: Int) = (steps * (steps + 1))/2
}