package day16

class LiteralValue private constructor(version: Int, val value: Long, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    override fun sumOfVerionNumbers() = version
    companion object{
        fun make(input: String){
            val version = input.getVersion()
            require(input.getID() == ID_LITERAL_VALUE) { "needed id 4, got ID ${input.substring(3..5).toInt(2)}"}
        }
    }
}