package day25

import common.Solution
import common.coordinates.Coordinate

class Day25: Solution {
    override val day = 25
    private val cucumbers = inputLines().map {it.toCharArray()}

    private val sizeX = cucumbers[0].size
    private val sizeY = cucumbers.size

    /**
     * Move all cucumbers until they stop moving.
     * Return the amount of moves.
     */
    override fun answer1(): Any {
        var snapShot = makeSnapshot()
        var iterations = 0
        while(true){
            moveCucumbers()
            iterations++
            val newState = makeSnapshot()
            if (snapShot == newState) return iterations
            snapShot = newState
        }
    }

    /**
     * No question 2 today!
     */
    override fun answer2(): Any = "Completed!"

    /**
     * Move cucumbers east, then south
     */
    private fun moveCucumbers(){
        moveEast()
        moveSouth()
    }

    /**
     * Move all eastbound cucumbers east
     */
    private fun moveEast() {
        val movingRight = ArrayList<Coordinate>()
        cucumbers.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == EAST && cucumbers[y][(x + 1) % sizeX] == EMPTY)
                    movingRight.add(Coordinate(x, y))
            }
        }
        movingRight.forEach {
            cucumbers[it.y][it.x] = EMPTY
            cucumbers[it.y][(it.x + 1) % sizeX] = EAST
        }
    }

    /**
     * Move all southbound cucumbers south
     */
    private fun moveSouth() {
        val movingSouth = ArrayList<Coordinate>()
        cucumbers.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == SOUTH && cucumbers[(y + 1) % sizeY][x] == EMPTY)
                    movingSouth.add(Coordinate(x, y))
            }
        }
        movingSouth.forEach {
            cucumbers[it.y][it.x] = EMPTY
            cucumbers[(it.y + 1) % sizeY][it.x] = SOUTH
        }
    }

    private fun makeSnapshot() = cucumbers.joinToString("\n") { it.joinToString("") }

    companion object{
        private const val EMPTY = '.'
        private const val EAST = '>'
        private const val SOUTH = 'v'
    }
}