package day1

import common.Solution
import common.extensions.printLines

class Day1: Solution {
    override val day = 1
    private val input = inputLines().map{it.toInt()}

    override fun answer1() = countIncreasing(input)

    override fun answer2() = countIncreasing(makeSlidingTripletSums())


    private fun makeSlidingTripletSums() = (0 until input.size - 2).map { input[it] + input[it + 1] + input[it + 2] }

    private fun countIncreasing(list: List<Int>): Int =
        list.indices.count {
            if (it == 0) false
            else list[it] > list[it-1]
        }
}