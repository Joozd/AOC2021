package day17

import common.Solution

class Day17: Solution {
    override val day = 17
    private val xTarget = 153..199
    private val yTarget = 75..114 /* y is counted down, inverse from input */

    /**
     * y always passes 0 on the way down
     * Highest velocity there is that one where it just hits the bottom of the y target
     * backwards up from there its max altitude is (1+2+3+4+5+6+...+n) where n=velocity-1
     * You can calculate such a series by (n * (n+1))/2
     */
    override fun answer1(): Any = (yTarget.last * (yTarget.last-1)) / 2

    /**
     * For all possible X and Y speeds
     * (e.g. those that do not immediately shoot past their target, including on the way back down, see #1 explanation)
     * check if they are a working combination (x and y move separate, but do need the same number of steps)
     */
    override fun answer2(): Any =
        (1..xTarget.last).sumOf{  vx->
            (yTarget.last*-1 .. yTarget.last).count { vy -> validSpeeds(vx, vy)}
        }

    private fun validSpeeds(vxInitial: Int, vyInitial: Int): Boolean{
        var xSpeed = vxInitial
        var ySpeed = vyInitial
        var xPos = 0
        var yPos = 0

        while (xPos <= xTarget.last && yPos <= yTarget.last){
            if (xPos in xTarget && yPos in yTarget) return true
            if (xSpeed != 0) xPos += xSpeed--
            yPos += ySpeed++
        }
        return false
    }
}