package day16.packets

class LessThan(version: Int,subPackets: List<Packet>, bitsUsedInConstructing: Int): Operator(version, subPackets, bitsUsedInConstructing) {
    override fun invoke(): Long{
        val values = values()
        return if (values[0] < values[1]) 1L else 0L
    }
}