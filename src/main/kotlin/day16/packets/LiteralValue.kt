package day16.packets

import day16.getVersion

class LiteralValue private constructor(version: Int, val value: Long, bitsUsedInConstructing: Int): Packet(version, bitsUsedInConstructing) {
    /**
     * The sum of this packets version numbers and the version numbers of any packets below it
     * This packet has no children, so just its sum
     */
    override fun sumOfVerionNumbers() = version

    /**
     * Perform the action this Packet performs
     * In this case, return the literal value.
     */
    override fun invoke(): Long = value

    override fun toString(): String = "LiteralValue v$version: $value"

    companion object{
        /**
         * Make a LiteralValue Packet
         */
        fun make(input: String): LiteralValue {
            val version = input.getVersion()
            val lvString = getLiteralValueString(input.substring(HEADER_LENGTH_LITERAL))
            val literalValue = lvString.toLong(2)
            return LiteralValue(version, literalValue, bitsLengthOfLVString(lvString) + HEADER_LENGTH_LITERAL)
        }

        /**
         * Get the string which holds this packets value
         * (as long as first bit after already processed data is '1', the next 4 bits after it are part of the data)
         */
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

        /**
         * Get the amount of bits the data in [lvString] uses in the raw input
         */
        private fun bitsLengthOfLVString(lvString: String) = 5 * lvString.length / 4
    }
}