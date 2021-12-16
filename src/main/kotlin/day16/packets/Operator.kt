package day16.packets

import common.extensions.printLines
import day16.getTypeID
import day16.getVersion

/**
 * An Operator is a Packet that performs a function
 */
abstract class Operator protected constructor(
    version: Int,
    private val subPackets: List<Packet>,
    bitsUsedInConstructing: Int
): Packet(version, bitsUsedInConstructing) {
    /**
     * The sum of this packets version numbers and the version numbers of any packets below it
     */
    override fun sumOfVerionNumbers() = version + subPackets.sumOf { it.sumOfVerionNumbers() }

    override fun toString() = "Operator v$version:\n${subPackets.printLines().prependIndent()}"

    /**
     * The values received by performing the actions of all Packets in [subPackets]
     */
    protected fun values(): List<Long> = subPackets.map { it() }


    companion object{
        private const val EXTRA_HEADER_LENGTH = 1
        private const val LENGTH_TYPE_BITS = '0'

        /**
         * Create one Packet from input string
         * This includes all child Packets
         */
        fun make(input: String): Operator {
            val version = input.getVersion()
            val typeID = input.getTypeID()
            val lengthType = input[6]
            val length = getLength(lengthType, input)
            val headerLength = HEADER_LENGTH_OPERATOR + bitsForContentLength(lengthType)
            val dataString = input.substring(headerLength)
            val packets = getPackets(dataString, lengthType, length)
            val bitsUsed =
                if (lengthType == LENGTH_TYPE_BITS) headerLength + length
                else headerLength + packets.sumOf { it.bitsUsedInConstructing }

            return when(typeID){
                ID_SUM -> Sum(version, packets, bitsUsed)
                ID_PRODUCT -> Product(version, packets, bitsUsed)
                ID_MINIMUM -> Minimum(version, packets, bitsUsed)
                ID_MAXIMUM -> Maximum(version, packets, bitsUsed)
                ID_LITERAL_VALUE -> error ("This constrctor is only for operators, got a literal")
                ID_GREATER_THAN -> GreaterThan(version, packets, bitsUsed)
                ID_LESS_THAN -> LessThan(version, packets, bitsUsed)
                ID_EQUAL -> Equal(version, packets, bitsUsed)
                else -> error ("Bad opcode $typeID")
            }
        }

        /**
         * Get all child Packets for this Packet
         */
        private fun getPackets(input: String, lengthType: Char, length: Int) =
            if (lengthType == LENGTH_TYPE_BITS)
                getPacketsFromNBits(input, length)
            else getNPackets(input, length)

        /**
         * Get all packets in the first n bits in [input] and return them as a list
         */
        private fun getPacketsFromNBits(input: String, length: Int): List<Packet> =
            makePackets(input.take(length))

        /**
         * Get the next [length] packets from [input]
         */
        private fun getNPackets(input: String, length: Int): List<Packet>{
            var workingString = input
            return (0 until length).map{
                val p = parseOnePacket(workingString)
                workingString = workingString.drop(p.bitsUsedInConstructing)
                p
            }
        }

        /**
         * The amount of bits used to describe the amount of data to create child Packets from
         */
        private fun bitsForContentLength(lengthType: Char) =
            if (lengthType == LENGTH_TYPE_BITS) 15 else 11

        /**
         * Get number from 11 or 15 bits after header, depending on [lengthType]
         */
        private fun getLength(lengthType: Char, input: String): Int {
            return input.substring(HEADER_LENGTH_OPERATOR until HEADER_LENGTH_OPERATOR + bitsForContentLength(lengthType)).toInt(2)
        }
    }
}