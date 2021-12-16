package day16.packets

class Minimum(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    /**
     * The minimum value of all subPackets' results
     */
    override fun invoke(): Long = values().minOrNull()!!
}