package day24

import common.Solution

class Day24: Solution {
    override val day = 24
    private val program = inputLines()

    private val monad = Monad.ofLines(program)

    /**
     * Get the highest possible model number
     */
    override fun answer1(): Any {
        //monad.check("97919997299495")
        return monad.max()
    }

    /**
     * Get the lowest possible model number
     */
    override fun answer2(): Any {
        //monad.check("51619131181131")
        return monad.min()
    }

}