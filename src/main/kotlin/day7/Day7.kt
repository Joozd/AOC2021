package day7

import common.Solution
import common.extensions.abs
import common.extensions.grabInts

/**
 * Calculate the minimum total fuel use from a list of points on a line to a target
 * Decided to just brute-force it. Looking for a minimum would be faster, but this already takes < 0,1 sec on my laptop.
 */
class Day7: Solution {
    override val day = 7

    private val crabs = readInput().grabInts()
    private val maxPosition = crabs.maxOrNull() ?: error ("error 1")
    private val possibleTargets = (0..maxPosition)


    /**
     * Linear fuel use: fuel = distance
     */
    override fun answer1(): Any =
        possibleTargets.minOf { t ->
            crabs.distancesFromTarget(t).sum()
        }

    /**
     * Increasing fuel use: fuel is (1+2+3+...+distance)
     */
    override fun answer2(): Any =
        possibleTargets.minOf { t ->
            crabs.incrementingFuelBurnDistancesToTarget(t).sum()
        }


    private fun List<Int>.distancesFromTarget(target: Int) =
        map { (it - target).abs()}

    private fun List<Int>.incrementingFuelBurnDistancesToTarget(target: Int) =
        map { getIncrementedFuelUse((it-target).abs()) }

    /**
     * @return (1+2+3+...+steps)
     */
    private fun getIncrementedFuelUse(steps: Int) = (steps * (steps + 1))/2
}