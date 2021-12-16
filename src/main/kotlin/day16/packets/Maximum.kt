package day16.packets

class Maximum(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    override fun invoke(): Long = values().maxOrNull()!!
}