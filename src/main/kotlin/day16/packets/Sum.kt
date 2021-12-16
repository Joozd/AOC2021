package day16.packets

class Sum(
    version: Int,subPackets: List<Packet>,
    bitsUsedInConstructing: Int
): Operator(version, subPackets, bitsUsedInConstructing) {
    override fun invoke(): Long = values().sum()
}