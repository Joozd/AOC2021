package day14

import common.Solution
import common.extensions.words
import java.lang.StringBuilder

/**
 * Initially did the long version as that was sufficient for question 1.
 * Left it in, but question 1 performs much faster with the solution for question 2.
 */
class Day14: Solution {
    override val day = 14
    private val input = inputLines()
    private val template: CharSequence = input.first()
    private val rules = input.drop(2)

    private lateinit var rulesMap: MutableMap<String, Char>

    override fun prepare() {
        rulesMap = rules.map{ rule ->
            rule.words().let{
                it.first() to it.last()[0]
            }
        }.toMap().toMutableMap()
    }

    /**
     * Count the elements after 10 iterations, return max - min
     */
    override fun answer1(): Any{
        val polymer = template.reactNTimes(10)
        return mostMinusLeastCommon(polymer)
    }

    /**
     * Count the elements after 40 iterations, return max - min
     */
    override fun answer2(): Any {
        val pairs = onlyPairs(40)
        return getBiggestDifference(countElementsInPairs(pairs, template))
    }

    /**
     * Do the reaction as in the example [n] times
     */
    private fun CharSequence.reactNTimes(n: Int): CharSequence{
        var result = this
        repeat(n){ result = result.react() }
        return result
    }

    /**
     * Perform the reaction as in the example
     */
    private fun CharSequence.react(): CharSequence{
        val result = StringBuilder(this.length * 2)
        result.append(first())
        var pointer = 0
        while (pointer <= length - 2){
            rulesMap[substring(pointer..++pointer)]?.let{
                result.append(it)
            }
            result.append(this[pointer])
        }
        return result
    }

    /**
     * Get the difference between the most and least common element in [polymer]
     */
    private fun mostMinusLeastCommon(polymer: CharSequence): Int{
        val grouping = count(polymer)
        return getBiggestDifference(grouping)
    }

    /**
     * Get the difference between the highest and lowest element in [grouping]
     */
    private fun getBiggestDifference(grouping: Map<Char, Int>) =
        grouping.values.maxOrNull()!! - grouping.values.minOrNull()!!

    /**
     * Get the difference between the highest and lowest element in [grouping]
     */
    private fun getBiggestDifference(grouping: Map<Char, Long>) =
        grouping.values.maxOrNull()!! - grouping.values.minOrNull()!!

    /**
     * Count the elements in a polymer and return that as a map
     */
    private fun count(polymer: CharSequence) = polymer.groupingBy { it }.eachCount()

    /**
     * Count the elements ina  Collection and return them as a map
     */
    private fun <T> count(collection: Collection<T>) = collection.groupingBy { it }.eachCount()

    /**
     * Do the same reaction only considering the amount of pairs and not their sequence
     */
    private fun onlyPairs(repeats: Int): Map<CharSequence, Long>{
        var pairs: Map<CharSequence, Long> = count(template.makePairsOfTwo()).map { it.key to it.value.toLong()}.toMap()

        repeat(repeats) {
            pairs = reactPairs(pairs)
        }
        return pairs
    }

    /**
     * React all pairs in this map
     */
    private fun reactPairs(pairs: Map<CharSequence, Long>): Map<CharSequence, Long>{
        val newPairs = HashMap<CharSequence, Long>()
        pairs.forEach { pairWithCount ->
            makeNewPairs(pairWithCount.key).forEach { newPair ->
                newPairs[newPair] = (newPairs[newPair] ?: 0L) + pairWithCount.value
            }
        }
        return newPairs
    }

    /**
     * Count all elements in these pairs.
     * All elements are used double except the very first and very last in this polymer
     */
    private fun countElementsInPairs(pairs: Map<CharSequence, Long>, template: CharSequence): Map<Char, Long>{
        val first = template.first()
        val last = template.last()
        val result = HashMap<Char, Long>()
        pairs.forEach {
            val one = it.key.first()
            val two = it.key.last()
            result[one] = (result[one] ?: 0L) + it.value
            result[two] = (result[two] ?: 0L) + it.value
        }
        result[first] = (result[first] ?: 0L) + 1
        result[last] = (result[last] ?: 0L) + 1

        return result.map{ it.key to it.value / 2L}.toMap()
    }

    /**
     * Make all possible pairs of two from this CharSequence (e.g. "ABCD"-> "AB", "BC", "CD")
     */
    private fun CharSequence.makePairsOfTwo() = (0 until length - 1).map { subSequence(it..it + 1) }

    /**
     * Make a single pair into two new pairs are per [rules]
     */
    private fun makeNewPairs(pair: CharSequence) =
        String(charArrayOf(pair[0], rulesMap[pair]!!, pair[1])).makePairsOfTwo()
}