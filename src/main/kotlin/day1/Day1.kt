package day1

import common.Solution

class Day1: Solution {
    override val day = 1
    private val input = inputLines().map{it.toInt()}

    // How many items in input are larger than the previous entry?
    override fun answer1() = countIncreasing(input)

    // How many triplets in input are larger than the previous triplet?
    override fun answer2() = countIncreasing(makeTripletSums())


    private fun makeTripletSums() =
        (0 until input.size - 2).map {
            input[it] + input[it + 1] + input[it + 2]
        }

    private fun countIncreasing(list: List<Int>): Int =
        (1 until list.size).count {
             list[it] > list[it-1]
        }
}