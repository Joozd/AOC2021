package day16

import common.Solution
import day16.packets.Packet

class Day16: Solution {
    override val day = 16

    private val input = readInput()
    private lateinit var outerPacket: Packet

    override fun prepare() {
        val inputBinary = input.toBinaryString()
        outerPacket = Packet.parseOnePacket(inputBinary)
    }

    /**
     * The sum of all version numbers of all packets
     */
    override fun answer1(): Any = outerPacket.sumOfVerionNumbers()

    /**
     * The result of the calculation done in the top Packet
     */
    override fun answer2(): Any = outerPacket()
}