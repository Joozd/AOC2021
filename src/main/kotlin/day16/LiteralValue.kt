package day16

class LiteralValue private constructor(version: Int, val value: Long, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    override fun sumOfVerionNumbers() = version
    companion object{
        const val HEADER_LENGTH = 6
        fun make(input: String): LiteralValue{
            println("input: $input")
            val version = input.getVersion()
            require(input.getID() == ID_LITERAL_VALUE) { "needed id 4, got ID ${input.substring(3..5).toInt(2)}"}
            val lvString = getLiteralValueString(input.substring(HEADER_LENGTH))
            val literalValue = lvString.toLong(2)
            println("lvString  = $lvString")
            return LiteralValue(version, literalValue, lvString.length + HEADER_LENGTH)
        }

        private fun getLiteralValueString(input: String): String{
            val blocks = ArrayList<String>()
            var index = 0
            while(input[index] == '1'){
                blocks.add(input.substring((index + 1) until (index+5)))
                index += 5
            }
            blocks.add(input.substring((index + 1) until (index+5)))
            return blocks.joinToString("")
        }
    }
}