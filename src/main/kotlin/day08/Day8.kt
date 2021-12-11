package day08

import common.Solution
import common.extensions.reverse
import common.extensions.sortString
import common.extensions.words

class Day8: Solution {
    override val day = 8

    private val input = inputLines()

    /**
     * How many words in the output parts of [input] have 2,3,4 or 7 letters?
     */
    override fun answer1(): Any {
        val outputDigits = input.map{ getCodedOutputDigits(it) }
        val wantedLengths = listOf(2,3,4,7)
        return outputDigits.flatten().count { it.length in wantedLengths}
    }

    /**
     * Get the numeric values of all output parts and sum them.
     */
    override fun answer2(): Any =
        input.sumOf { decodeLine(it) }


    /**
     * Grab the output words from a line
     */
    private fun getCodedOutputDigits(inputLine: String): List<String> = inputLine.drop(START_OF_OUTPUT).words()

    /**
     * Decode a line, return its displayed value
     */
    private fun decodeLine(line: String): Int{
        val decoder = makeDecoderMap(line)
        return line.decode(decoder)
    }

    /**
     * Make a decoder map.
     * Decoder map holds [input letters, sorted a-g] to [output digit]
     */
    private fun makeDecoderMap(line:String): Map<String, Int>{
        val words =line.take(END_OF_ANALYSIS).words()
        val decoderMap = HashMap<Int, String>()
        // 1, 4, 7 and 8 are trivial:
        decoderMap[1] = words.first{ it.length == 2}
        decoderMap[4] = words.first{ it.length == 4}
        decoderMap[7] = words.first{ it.length == 3}
        decoderMap[8] = words.first{ it.length == 7}

        // 2, 3 and 5 have 5 segments, only 3 overlaps with 7
        val twoThreeFive = words.filter { it.length == 5 }
        decoderMap[3] = twoThreeFive.findOverlapping(decoderMap[7])
        val twoFive = twoThreeFive.filter { it != decoderMap[3]}

        // 0, 6 and 9 have 6 segments, only 9 overlaps with 4
        val zeroSixNine = words.filter { it.length == 6 }
        decoderMap[9] = zeroSixNine.findOverlapping(decoderMap[4])
        val zeroSix = zeroSixNine.filter { it != decoderMap[9] }

        // 2 is the one that has the segment missing from 9; 5 is the other one in twoFive
        val segmentE = "abcdefg".first { it !in decoderMap[9]!!}
        decoderMap[2] = twoFive.first { segmentE in it}
        decoderMap[5] = twoFive.first { it != decoderMap[2] }

        //0 overlaps with 7; 6 is the other one in zeroSix
        decoderMap[0] = zeroSix.findOverlapping(decoderMap[7])
        decoderMap[6] = zeroSix.first { it != decoderMap[0] }

        return decoderMap.toSortedStringToIntMap()
    }

    /**
     * Decode a line
     */
    private fun String.decode(decoderMap: Map<String, Int>): Int{
        val output = getCodedOutputDigits(this).map { it.sortString()}
        return output.map {decoderMap[it]!!}.joinToString("").toInt()
    }

    /**
     * changes a Map<Int, String> to a Map<String, Int>, where the strings are all sorted a-g internally
     */
    private fun Map<Int,String>.toSortedStringToIntMap() = this.reverse().map {
        it.key.sortString() to it.value
    }.toMap()

    /**
     * Check if this string contains all the letters in [other] (and maybe more)
     */
    private fun String.holdsAllLettersOf(other: String?) = other?.all{ it in this } ?: false

    /**
     * Find a String in this list that holds all the letters of [overlap] (and maybe more)
     */
    private fun List<String>.findOverlapping(overlap: String?) =
        this.first { it.holdsAllLettersOf(overlap) }

    companion object{
        private const val START_OF_OUTPUT = 61
        private const val END_OF_ANALYSIS = 58
    }
}