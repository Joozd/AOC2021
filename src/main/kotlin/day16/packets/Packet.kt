package day16.packets

import day16.containsAnyOnes
import day16.getTypeID

/**
 *  A packet. See https://adventofcode.com/2021/day/16
 */
abstract class Packet protected constructor(val version: Int, val bitsUsedInConstructing: Int){
    /**
     * The sum of this packets version numbers and the version numbers of any packets below it
     */
    abstract fun sumOfVerionNumbers(): Int

    /**
     * Perform the action this Packet performs
     */
    abstract operator fun invoke(): Long

    companion object{
        /**
         * Create one packet from input string
         */
        fun parseOnePacket(input: String): Packet {
            // literals and operators require different parsing
            return when (input.getTypeID()) {
                ID_LITERAL_VALUE -> LiteralValue.make(input)
                else -> Operator.make(input)
            }
        }

        /**
         * Make all packets in this string and return them as a list
         */
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
        //Length of Header of a
        const val HEADER_LENGTH_LITERAL = 6
        const val HEADER_LENGTH_OPERATOR = 7

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