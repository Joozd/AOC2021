import common.coordinates.Coordinate
import day11.Octopus
import org.junit.Test

class Test {
    @Test
    fun test(){
        val testOcto = Octopus(3,6,-1)
        val allNeighbors = (5..7).map{ y ->
            (2..4).map { x ->
                Coordinate(x,y)
            }
        }.flatten().filter { it != Coordinate(3,6)}
        println(allNeighbors)
        assert(testOcto.eightNeighbors().all { it in allNeighbors})
        assert(testOcto.eightNeighbors().none { it !in allNeighbors})
    }


    fun String.holdsAllLettersOf(other: String) = other.all{ it in this }
}