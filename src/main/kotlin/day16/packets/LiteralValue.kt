package day16.packets

import day16.getVersion

class LiteralValue private constructor(version: Int, val value: Long, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    override fun sumOfVerionNumbers() = version
    override fun invoke(): Long = value

    override fun toString(): String = "LiteralValue v$version: $value"

    companion object{
        fun make(input: String): LiteralValue {
            val version = input.getVersion()
            // next line might is a sanity check for debugging
            // require(input.getID() == ID_LITERAL_VALUE) { "needed id $ID_LITERAL_VALUE, got ID ${input.substring(3..5).toInt(2)}"}
            val lvString = getLiteralValueString(input.substring(HEADER_LENGTH))
            val literalValue = lvString.toLong(2)
            return LiteralValue(version, literalValue, bitsLengthOfLVString(lvString) + HEADER_LENGTH)
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

        private fun bitsLengthOfLVString(lvString: String) = 5 * lvString.length / 4
    }
}