
import common.extensions.times
import day21.Dice
import day21.DiracDice
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val l1 = listOf(1,2,3)
        val r = listOf(1,2,3,1,2,3,1,2,3,1,2,3)
        assertEquals(r, l1 * 4)

        val expectedResult = listOf(
            3 to 1,
            4 to 3,
            5 to 6,
            6 to 7,
            7 to 6,
            8 to 3,
            9 to 1).toMap()
        val diracDice = DiracDice(3)
        assertEquals(expectedResult, diracDice.roll())
        assertEquals (3*3*3, diracDice.roll().values.sum())

        val testPair = 3L to 7L
        val test = testPair * 10
        assertEquals(30L to 70L, test)
        assertEquals(33L to 77L, listOf(testPair, test).sum())

    }
    private operator fun Pair<Long, Long>.times(n: Int) = this.first * n to this.second * n
    private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = this.first + other.first to this.second + other.second

    private fun List<Pair<Long, Long>>.sum() = this.reduce { acc, pair -> acc + pair }
}

