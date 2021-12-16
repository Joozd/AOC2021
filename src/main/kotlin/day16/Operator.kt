package day16

open class Operator protected constructor(version: Int, private val subPackets: List<Packet>, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    override fun sumOfVerionNumbers() = subPackets.sumOf { it.sumOfVerionNumbers() }
}