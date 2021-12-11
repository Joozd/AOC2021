package day02

import common.coordinates.Coordinate
import common.Solution
import common.coordinates.Vector

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
            'u' -> this + Vector(0, amount * -1)
            'd' -> this + Vector(0, amount)
            'f' -> this + Vector(amount, 0)
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

    private fun Coordinate.moveWithAim(line: String, aim: Int): Coordinate {
        if (line.first() != 'f') return this
        val amount = line.lineValue()
        return this + Vector(amount, amount* aim)
    }

    private fun String.lineValue(): Int = last().toString().toInt()
}