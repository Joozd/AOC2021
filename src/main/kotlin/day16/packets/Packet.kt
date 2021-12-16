package day16.packets

import day16.containsAnyOnes
import day16.getTypeID

abstract class Packet protected constructor(val version: Int, val bitsUsedInConstructing: Int){
    abstract fun sumOfVerionNumbers(): Int
    abstract operator fun invoke(): Long

    companion object{
        fun parseOnePacket(input: String): Packet {
            // println("\nParsing $input")
            // println("type: ${input.getID()}")

            return when (input.getTypeID()) {
                ID_LITERAL_VALUE -> LiteralValue.make(input)
                else -> Operator.make(input)

            }
        }

        fun makePackets(input: String): List<Packet>{
            var workingString = input
            val foundPackets = ArrayList<Packet>()
            while (workingString.containsAnyOnes()) {
                val p = parseOnePacket(workingString)
                foundPackets.add(p)
                workingString = workingString.drop(p.bitsUsedInConstructing)
            }
            return foundPackets
        }

        const val HEADER_LENGTH = 6

        const val ID_SUM = 0
        const val ID_PRODUCT = 1
        const val ID_MINIMUM = 2
        const val ID_MAXIMUM = 3
        const val ID_LITERAL_VALUE = 4
        const val ID_GREATER_THAN = 5
        const val ID_LESS_THAN = 6
        const val ID_EQUAL = 7
    }
}