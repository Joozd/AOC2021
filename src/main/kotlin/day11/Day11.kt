package day11

import common.Solution

class Day11: Solution {
    override val day = 11
    private val input = inputLines()

    override fun answer1(): Any{
        generateImages()
        val cave = OctopusCave(input)
        var flashes = 0
        repeat(100){
            flashes += cave.tick()
        }
        return flashes
    }

    override fun answer2(): Any {
        val cave = OctopusCave(input)
        var ticks = 0
        var flashedThisTick = 0
        while (flashedThisTick != cave.size){
            flashedThisTick = cave.tick()
            ticks++
        }
        return ticks
    }

    private fun generateImages(){
        val cave = OctopusCave(input)
        val dir = "C:\\temp\\aoc\\11\\"
        repeat(400){
            cave.exportPNG("$dir$it.png")
            cave.tick()
        }
    }
}