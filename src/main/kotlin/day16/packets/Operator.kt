package day16.packets

import common.extensions.printLines
import day16.getTypeID
import day16.getVersion

abstract class Operator protected constructor(version: Int, protected val subPackets: List<Packet>, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    override fun sumOfVerionNumbers() = version + subPackets.sumOf { it.sumOfVerionNumbers() }
    override fun toString() = "Operator v$version:\n${subPackets.printLines().prependIndent()}"

    protected fun values(): List<Long> = subPackets.map { it() }

    companion object{
        private const val EXTRA_HEADER_LENGTH = 1
        private const val LENGTH_TYPE_BITS = '0'
        private const val LENGTH_TYPE_PACKETS = '1'


        fun make(input: String): Operator {
            val version = input.getVersion()
            val typeID = input.getTypeID()
            val lengthType = input[6]
            val length = getLength(lengthType, input)
            val headerLength = HEADER_LENGTH + EXTRA_HEADER_LENGTH + bitsForContentLength(lengthType)
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

        private fun getPackets(input: String, lengthType: Char, length: Int) =
            if (lengthType == LENGTH_TYPE_BITS)
                getPacketsFromNBits(input, length)
            else getNPackets(input, length)

        private fun getPacketsFromNBits(input: String, length: Int) =
            makePackets(input.take(length))

        private fun getNPackets(input: String, length: Int): List<Packet>{
            var workingString = input
            return (0 until length).map{
                val p = parseOnePacket(workingString)
                workingString = workingString.drop(p.bitsUsedInConstructing)
                p
            }
        }

        private fun bitsForContentLength(lengthType: Char) =
            if (lengthType == LENGTH_TYPE_BITS) 15 else 11

        /**
         * Get number from 11 or 15 bits after header, depending on [lengthType]
         */
        private fun getLength(lengthType: Char, input: String): Int {
            val h = HEADER_LENGTH + EXTRA_HEADER_LENGTH
            return input.substring(h until h + bitsForContentLength(lengthType)).toInt(2)
        }

    }
}