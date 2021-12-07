package day6

/**
 * Simulates the reproduction of lanternfish
 * They won't stay in order, because as wel all know, fish tend to swim around.
 */
class FishReproducer(initialFishes: List<Int>) {
    private val counts = LongArray(LIST_SIZE) { i -> initialFishes.count { it == i}.toLong() }

    /**
     * Spawns new fish, resets '0'- fish to 6 and counts down all (other) fish.
     */
    fun tick(){
        spawnFish()
        countDownFishes()
    }

    fun count() = counts.sum()

    /**
     * Spawn fishes and reset '0' fish. Both of these need to be counted down!
     */
    private fun spawnFish(){
        counts[7] += counts[0] // reset timer
        counts[9] = counts[0]  // spawn fish
    }

    /**
     * Reduces the number of all fish.
     */
    private fun countDownFishes(){
        (1 until LIST_SIZE).forEach{ i ->
            counts[i-1] = counts[i]
        }
    }

    companion object{
        private const val LIST_SIZE = 11
    }
}