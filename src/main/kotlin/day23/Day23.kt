package day23

import common.Solution
import common.buildCoordinatesWithValuesMap

class Day23: Solution {
    override val day = 23
    private val input = inputLines()

    private val input2 = """#############
#...........#
###D#B#B#A###
  #D#C#B#A#
  #D#B#A#C#
  #C#C#D#A#
  #########""".lines()


    override fun answer1(): Any {
        val initialNetwork = buildCoordinatesWithValuesMap(input).filter {it.value in ".ABCD"}
        val initialState = State(initialNetwork)
        return initialState()
    }

    /**
     * 2047483693 too high
     */
    override fun answer2(): Any {
        val initialNetwork = buildCoordinatesWithValuesMap(input2).filter {it.value in ".ABCD"}
        val initialState = State(initialNetwork)
        return initialState()
    }
}

