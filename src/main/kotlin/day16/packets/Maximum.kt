package day16.packets

class Maximum(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    /**
     * The maximum value of all subPackets' results
     */
    override fun invoke(): Long = values().maxOrNull()!!
}