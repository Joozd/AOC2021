package day10

import common.Solution

class Day10: Solution {
    override val day = 10
    private val input = inputLines()

    private val scoreTable1 = listOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    ).toMap()

    private val scoreTable2 = listOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    ).toMap()

    /**
     * Get bad lines, and find score of first bad character in that line
     */
    override fun answer1(): Any {
        val badChars = input.mapNotNull { getBadCharacter(it) }
        return badChars.sumOf{ scoreTable1[it]!!}
    }

    /**
     * Complete all incomplete lines, calculate scores for those lines and return middle score
     */
    override fun answer2(): Any {
        val incompleteLines = input.filter { getBadCharacter(it) == null }
        val reducedLines = incompleteLines.map { reduceChunk(it) }
        val result = getClosingStringsFor(reducedLines)
        val scores = result.map { calculateScore(it) }
        return scores.sorted()[scores.size / 2]
    }

    /**
     * Get bad closing characters in a line, or null if no bad characters in line
     */
    private fun getBadCharacter(line: String) =
        reduceChunk(line).firstOrNull { it in ")]}>" }

    /**
     * Remove all complete empty chunks from line
     */
    private fun reduceChunk(chunk: String): String{
        val emptyChunkRegex = """(\(\)|\[]|\{}|<>)""".toRegex()
        var size = -1
        var workingString = chunk
        while(workingString.length != size){
            size = workingString.length
            workingString = workingString.replace(emptyChunkRegex, "")
        }
        return workingString
    }

    /**
     * Generate closing strings for all lines
     */
    private fun getClosingStringsFor(lines: List<String>) =
        lines.map {getClosingStringFor(it)}

    /**
     * Generate all characters needed to close all chunks in this line
     */
    private fun getClosingStringFor(line: String) =
        line.reversed().map { getClosingchar(it)}.joinToString("")

    /**
     * Get closing chars for an opening char
     */
    private fun getClosingchar(openingChar: Char) =
        if (openingChar == '(') ')'
        else openingChar + 2 // <, { and [ increased by two are >, } and ]

    /**
     * Calculate score for a line
     */
    private fun calculateScore(line: String): Long{
        var currentScore = 0L
        line.forEach { c ->
            currentScore *= 5
            currentScore += scoreTable2[c]!!
        }
        return currentScore
    }
}