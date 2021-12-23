package day23

import common.coordinates.Coordinate
import common.coordinates.CoordinateWithValue
import common.dijkstra.Dijkstra

class State(private val allRooms: List<CoordinateWithValue<Char>>){

    private val routeFinder = Dijkstra<Int>(16){ node ->
        allRooms.firstOrNull { it == node && it.value == EMPTY_ROOM }
    }

    /**
     * Return the energy used in the least-energy solution from this state to fully sorted
     */
    operator fun invoke(): Int {
        if (isCompleted()) return 0
        val moves = allMoves()
        return if (allMoves().isEmpty()) Int.MAX_VALUE - 99999999
        else moves.minOf{
            it.first() + it.second
        }
    }

    private fun isCompleted() = allRooms.none{ it.value in AMPHIPODS }

    private fun allMoves(): List<Pair<State, Int>> =
        moveableAmphipods().map{ roomWithAmphipod ->
            val pm = possibleMovesForAmphipod(roomWithAmphipod)
            //If this move moves an amphipod to its target, this will be the only possible move
            if(pm.size == 1 && pm.first() == getTargetFor(roomWithAmphipod))
                return listOf(move(roomWithAmphipod, pm.first()))

            pm.filter { it.isAllowedMove(roomWithAmphipod)}.map{ moveTo ->
                move(roomWithAmphipod, moveTo)
            }
        }.flatten()



    // Returns a list of all rooms with an amphipod in it
    private fun amphipods() = allRooms.filter { it.value in AMPHIPODS }

    private fun moveableAmphipods() = amphipods().filter{ roomWithAmphipod ->
        roomWithAmphipod.fourNeighbors().any { it.isAvailableNeighbor() }
    }

    private fun Coordinate.isAvailableNeighbor(): Boolean {
        val v = allRooms.firstOrNull { it == this }?.value ?: return false
        return v == EMPTY_ROOM
    }

    private fun possibleMovesForAmphipod(roomWithAmphipod: CoordinateWithValue<Char>): List<CoordinateWithValue<Char>> {
        //println("Looking at moves for $roomWithAmphipod")
        val allReachableNodes = routeFinder.findAllReachableNodes(roomWithAmphipod).map { it as CoordinateWithValue<Char>}
        //println("found ${allReachableNodes.size} reachable nodes")
        return allReachableNodes.filter { it == getTargetFor(roomWithAmphipod) }
            .takeIf { it.isNotEmpty() }
            ?: allReachableNodes.filter { it in FREE_SPACES }
    }

    private fun getTargetFor(amphipod: CoordinateWithValue<Char>): Coordinate=
        allRooms.filter { it.x == targetColumn[amphipod.value]}.maxByOrNull{ it.y }!!


    /**
     * check if this is an allowed move:
     * - it moves an amphipod to a target
     * - it moves and amphipod that is not D and not yet in a free space to a free space
     */
    private fun CoordinateWithValue<Char>.isAllowedMove(from: CoordinateWithValue<Char>): Boolean =
        this.y > 1 || (this in FREE_SPACES && from.value != 'D' && from !in FREE_SPACES)


    /**
     * Move an amphipod, return the new state as a freshly copied object paired with the energy spent to get to this state
     */
    private fun move(from: CoordinateWithValue<Char>, to: CoordinateWithValue<Char>): Pair<State, Int>{
        val copiedState = allRooms.filter {it != to}.map{it.copy()}
        val spentEnergy = routeFinder.findDistance(from, to)!! * energy[from.value]!!
        copiedState.first{it == from}.value = EMPTY_ROOM
        return if(to.y != 1){ // moving to a target coordinate
            //println("${from.value} moved from $from to its target $to")
            State(copiedState) to spentEnergy
        } else State(copiedState + to.copy(from.value)) to spentEnergy
    }





    companion object{
        private const val X_COORDINATE_A = 3
        private const val X_COORDINATE_B = 5
        private const val X_COORDINATE_C = 7
        private const val X_COORDINATE_D = 9

        private val targetColumn = listOf(
            'A' to X_COORDINATE_A,
            'B' to X_COORDINATE_B,
            'C' to X_COORDINATE_C,
            'D' to X_COORDINATE_D
        ).toMap()

        private const val AMPHIPODS = "ABCD"
        private const val EMPTY_ROOM = '.'

        private val energy = listOf(
            'A' to 1,
            'B' to 10,
            'C' to 100,
            'D' to 1000
        ).toMap()

        private val FREE_SPACES = listOf(
            Coordinate(1,1),
            Coordinate(2,1),
            Coordinate(4,1),
            Coordinate(6,1),
            Coordinate(8,1),
            Coordinate(10,1),
            Coordinate(11,1)
        )
    }
}