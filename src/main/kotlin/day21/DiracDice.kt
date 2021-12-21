package day21

import common.countEqualElements

class DiracDice(private val sides: Int) {
    private val distribution: Map<Int, Int> = makeDistribution()

    /**
     * roll() will return a distribution of all possible roll results.
     * result is value to weight
     */
    fun roll() = distribution

    private fun makeDistribution() =
        countEqualElements(
            (1..sides).map{ d1 ->
                (1..sides).map{ d2 ->
                    (1..sides).map{ d3 ->
                        d1 + d2 + d3
                    }
                }.flatten()
            }.flatten()
        )
}