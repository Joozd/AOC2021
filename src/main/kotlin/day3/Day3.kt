package day3

import common.BinaryNumber
import common.Solution

class Day3: Solution {
    override val day = 3
    private val input = inputLines()
    private lateinit var inputBinaries: List<BinaryNumber>

    override fun prepare() {
        inputBinaries = input.map { BinaryNumber(it) }
    }

    override fun answer1(): Any {
        val gammaRate = getGammaRate(inputBinaries)
        val epsilonRate = !gammaRate
        return (gammaRate * epsilonRate).toString(10)
    }

    override fun answer2(): Any{
        val oxyRate = getOxyRate(inputBinaries)
        val scrubRate = getScrubRate(inputBinaries)
        return (oxyRate * scrubRate).toString(10)
    }

    /******************
     * Main Functions *
     ******************/

    private fun getGammaRate(inputBinaries: List<BinaryNumber>): BinaryNumber = BinaryNumber(
        inputBinaries.first().indices.map{ bit ->
            inputBinaries.mostCommonBitAtIndex(bit)
        })

    private fun getOxyRate(binaryNumbers: List<BinaryNumber>, index: Int = 0): BinaryNumber {
        val wantedValue = binaryNumbers.mostCommonBitAtIndex(index)
        val result = binaryNumbers.findMatchingNumbers(index, wantedValue)
        if (result.size > 1) return getOxyRate(result, index + 1)
        return result.first()
    }

    private fun getScrubRate(binaryNumbers: List<BinaryNumber>, index: Int = 0): BinaryNumber {
        val wantedValue = binaryNumbers.leastCommonBit(index)
        val result = binaryNumbers.findMatchingNumbers(index, wantedValue)
        if (result.size > 1) return getScrubRate(result, index + 1)
        return result.first()
    }

    /********************
     * Helper functions *
     ********************/

    private fun List<BinaryNumber>.findMatchingNumbers(index: Int, wantedValue: Boolean) =
        this.filter { it[index] == wantedValue }

    private fun List<BinaryNumber>.relevantBitsOnly(bit: Int) =
        map { it[bit] }

    private fun List<BinaryNumber>.mostCommonBitAtIndex(bit: Int) =
        mostCommon(relevantBitsOnly(bit))

    /**
     * Returns most common bit in a list of bits, or true if tied.
     */
    private fun mostCommon(bits: List<Boolean>): Boolean {
        val cutoff = (bits.size.toFloat() / 2)
        val result = bits.count { it }
        return result >= cutoff
    }

    private fun List<BinaryNumber>.leastCommonBit(bit: Int) =
        !mostCommonBitAtIndex(bit)
}

