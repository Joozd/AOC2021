package day16.packets

import common.extensions.product

class Minimum(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    override fun invoke(): Long = values().minOrNull()!!
}