package day18

import common.Solution
import common.extensions.grabInts
import common.extensions.pow
import kotlin.text.StringBuilder

class Day18: Solution {
    override val day = 18

    private val input = inputLines()

    /**
     * Sum all SlugFishNumbers
     */
    override fun answer1(): Any{
        val sum = addAndReduceLines(input)
        val slugFishData = makeSlugFishData(sum)
        return slugFishData()
    }

    /**
     * Find the largest magnitude of any combination of two SlugFishNumbers
     */
    override fun answer2(): Any? = input.mapIndexed { i1, s1 ->
        input.filterIndexed { i2, _ ->  i1 != i2 }.maxOf{ s2 ->
            makeSlugFishData(addAndReduceLines(listOf(s1, s2)))()
        }
    }.maxOrNull()

    /**
     * Make a SlugFishData object of a String
     */
    private fun makeSlugFishData(sum: CharSequence) = SlugFishData.parse(sum.iterator())

    /**
     * Sum a list of SlugFishNumbers. Numbers are reduced after every addition.
     */
    private fun addAndReduceLines(lines: Collection<CharSequence>): CharSequence{
        var currentLine: CharSequence = reduceLine(lines.first())
        lines.drop(1).forEach{
            currentLine = reduceLine(add(currentLine, it))
        }
        return currentLine
    }

    /**
     * Add two SlugFish numbers, represented by Strings. This will need to be reduced.
     */
    private fun add(p1: CharSequence, p2: CharSequence) = "[$p1,$p2]"

    /**
     * Reduce a SlugFishNumber
     */
    private fun reduceLine(line: CharSequence): CharSequence{
        var l = line
        while(true){
            l = explode(l) ?: split(l) ?: return l
        }
    }

    /**
     * explode the first pair that is depth 5
     * Returns exploded line or null if nothing changed
     */
    private fun explode(line: CharSequence): CharSequence?{
        val newLine = StringBuilder(line)
        val positionOfExplodingPair = findExplodingPair(line) ?: return null
        val lastNumberWithIndex = getLastNumberWithIndex(line.take(positionOfExplodingPair.first))
        val nextNumberWithIndex = getFirstNumberWithIndexAfter(line, positionOfExplodingPair.last)
        val numbersInExplodingPair = line.substring(positionOfExplodingPair).grabInts()
        /*
        numbersInExplodingPair [1] is added to nextNumber
        positionOfExplodingPair is replaced by "0"
        numbersInExplodingPair [0] is added to lastNumber
        Do this in this order(right to left) so we don't mess up indices
        */
        newLine.addAndReplace(nextNumberWithIndex, numbersInExplodingPair[1])
        newLine.deleteRange(positionOfExplodingPair.first, positionOfExplodingPair.last + 1)
        newLine.insert(positionOfExplodingPair.first, "0")
        newLine.addAndReplace(lastNumberWithIndex, numbersInExplodingPair[0])
        return newLine
    }

    /**
     * Find the first two-digit number and split it
     * @return the updated line or null if nothing changed.
     */
    private fun split(line: CharSequence): CharSequence? =
        """\d{2}""".toRegex().find(line)?.let {
            val sb = StringBuilder(line)
            sb.deleteRange(it.range.first, it.range.last + 1)
            val number = it.value.toInt()
            val l = number/2
            val r = (number+1) / 2
            sb.insert(it.range.first, "[$l,$r]")
        }

    /**
     * Returns range of indices of the first exploding pair, or null if no pairs explode
     */
    private fun findExplodingPair(line: CharSequence): IntRange?{
        var depth = 0
        line.forEachIndexed{ i, c ->
            if (c == '[') depth++
            if (c == ']') depth--
            if (depth == 5) {
                var length = 1
                while (line[i + length] != ']') length++
                return i..i + length
            }
        }
        return null
    }

    /**
     * Return a pair of <Value of last number in String, Index of first digit of last number in String>
     */
    private fun getLastNumberWithIndex(line: CharSequence): Pair<Int, Int>?{
        var foundNumber = 0
        var digitsInNumber = 0
        (line.length-1 downTo 0).forEach {
            val c = line[it]
            when {
                c.isDigit() -> {
                    foundNumber += c.digitToInt() * 10.pow(digitsInNumber)
                    digitsInNumber++
                }
                digitsInNumber != 0 -> return foundNumber to it + 1
            }
        }
        return null
    }

    /**
     * Return a pair of <Value of first number in String, Index of first digit of first number in String>
     * It will start looking after index [offset]
     */
    private fun getFirstNumberWithIndexAfter(line: CharSequence, offset: Int): Pair<Int, Int>? =
        """\d+""".toRegex().find(line, offset)?.let{
            it.value.toInt() to it.range.first
        }

    /**
     * Add and replace number to NumberWitRange. If NumberWithRange == null, do nothing.
     * This works in place.
     */
    private fun StringBuilder.addAndReplace(numberWithIndex: Pair<Int, Int>?, numberToAdd: Int){
        if (numberWithIndex == null) return
        val rangeOfNumber = numberWithIndex.second .. numberWithIndex.second + numberWithIndex.first.toString().length
        delete(rangeOfNumber.first, rangeOfNumber.last)
        insert(rangeOfNumber.first, numberWithIndex.first + numberToAdd)
    }
}