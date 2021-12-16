package day16.packets

class Equal(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    /**
     * 1  if first and second child Packets are equal, else 0
     */
    override fun invoke(): Long{
        val values = values()
        return if (values[0] == values[1]) 1L else 0L
    }
}