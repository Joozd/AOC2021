package common

import java.io.File

interface Solution {
    val day: Int
    fun answer1(): String

    fun answer2(): String

    operator fun invoke() {
        println("Answers for day $day:")
        println("1:")
        println(answer1())
        println("2:")
        println(answer2())
    }

    fun inputLines() = readInput().lines()
    fun readInput() = File("inputs\\$day.txt").readText()
}