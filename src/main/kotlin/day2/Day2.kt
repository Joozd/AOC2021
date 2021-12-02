package day2

import common.Coordinate
import common.Solution

class Day2: Solution {
    override val day = 2
    private val inputs = inputLines()

    override fun answer1(): Any {

        var currentPos = Coordinate(0,0)
        inputs.forEach{
            currentPos = currentPos.updatePos(it)
        }
        return currentPos.x * currentPos.y
    }

    override fun answer2(): Any {
        var currentPos = Coordinate(0,0)
        var aim = 0
        inputs.forEach{
            aim = aim.updateAim(it)
            currentPos = currentPos.moveWithAim(it, aim)
        }
        return currentPos.x * currentPos.y
    }

    private fun Coordinate.updatePos(line: String): Coordinate {
        val amount = line.lineValue()
        return when (line[0]) {
            'u' -> Coordinate(0, amount * -1) + this
            'd' -> Coordinate(0, amount) + this
            'f' -> Coordinate(amount, 0) + this
            else -> error("error")
        }
    }

    private fun Int.updateAim(line: String): Int {
        val amount = line.lineValue()
        return when (line[0]) {
            'u' -> this - amount
            'd' -> this + amount
            'f' -> this
            else -> error("error")
        }
    }

    private fun Coordinate.moveWithAim(line: String, aim: Int): Coordinate{
        val amount = line.lineValue()
        if (line.first() != 'f') return this
        return Coordinate(amount, amount* aim) + this
    }

    private fun String.lineValue(): Int = last().toString().toInt()
}