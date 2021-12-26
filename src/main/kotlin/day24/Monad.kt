package day24

import common.extensions.grabInts
import common.extensions.printLines
import java.util.*

/**
 * Monad checks if a model number is valid, and can also output max and min acceptable model numbers
 */
class Monad private constructor(private val program: List<List<Int>>) {
    // List of which ADD goes with which POP (indices of [program])
    private val pairs: List<MonadPair> = makePairs()

    /**
     * Check if a number is a valid Model Number
     */
    fun check(serialNumber: String): Boolean{
        val digits = serialNumber.map { it.digitToInt() }
        return pairs.all{ p ->
            println("${digits[p.secondIndex]} - ${digits[p.firstIndex]} == ${p.differenceBetweenDigits}")
            digits[p.secondIndex] - digits[p.firstIndex] == p.differenceBetweenDigits
        }
    }

    /**
     * Get the max allowed model number
     */
    fun max(): String{
        println(pairs.printLines())
        val result = IntArray(14){0}
        pairs.forEach{
            result.insertPair(it, MAX)
        }
        return result.joinToString("")
    }

    /**
     * Get the min allowed model number
     */
    fun min(): String{
        println(pairs.printLines())
        val result = IntArray(14){0}
        pairs.forEach{
            result.insertPair(it, MIN)
        }
        return result.joinToString("")
    }

    /**
     * Make NOMAD pairs!
     */
    private fun makePairs(): List<MonadPair>{
        val stack = LinkedList<Int>()
        val pairs = ArrayList<MonadPair>()
        program.forEachIndexed{ index, values ->
            if(values[INDEX_X] > 9) stack.add(index)
            else pairs.add(buildPair(stack.removeLast(), index))
        }
        require (stack.isEmpty())
        println(pairs.printLines())
        return pairs
    }

    /**
     * Build a pair of numbers according to the rules in [program]
     */
    private fun buildPair(firstIndex: Int, lastIndex: Int): MonadPair{
        val diff = program[firstIndex][INDEX_Y] + program[lastIndex][INDEX_X]
        return MonadPair(firstIndex, lastIndex, diff)
    }

    /**
     * Insert a pair of numbers according to the rules in [program]
     * [max] = insert highest if true, or lowest if false
     */
    private fun IntArray.insertPair(pair: MonadPair, max: Boolean){
        val values = makePair(pair.differenceBetweenDigits, max)
        this[pair.firstIndex] = values.first
        this[pair.secondIndex] = values.second
    }

    /**
     * Make a pair of highest or lowest 2 digits (1-9) that can be made with a difference of [differenceBetweenDigits]
     */
    private fun makePair(differenceBetweenDigits: Int, max: Boolean): Pair<Int, Int>{
        val v1Max = if (differenceBetweenDigits < 0) 9 else 9 - differenceBetweenDigits
        val v1Min = if (differenceBetweenDigits > 0) 1 else 1 - differenceBetweenDigits
        return if (max) v1Max to v1Max + differenceBetweenDigits
        else v1Min to v1Min + differenceBetweenDigits
    }

    /**
     * A pair of index(digit) with index(its paired digit) and the difference between their values
     * [differenceBetweenDigits] is value2 - value1
     */
    private data class MonadPair(val firstIndex: Int, val secondIndex: Int, val differenceBetweenDigits: Int)


    companion object{
        /**
         * This makes a Monad program from input lines
         */
        fun ofLines(lines: List<String>): Monad{
            val segments = lines.drop(1).joinToString("\n").split("inp w\n").map{ it.lines()}
            //println(segments.printLines())

            val values = segments.map{ listOf(it[4].grabInts().first(), it[14].grabInts().first())}
            return Monad(values)
        }
        private const val INDEX_X = 0
        private const val INDEX_Y = 1

        private const val MAX = true
        private const val MIN = false
    }
}