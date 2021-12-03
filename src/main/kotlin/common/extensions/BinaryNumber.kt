package common.extensions

class BinaryNumber(private val backingField: Long, val length: Int = Long.SIZE_BITS): Number(), Comparable<BinaryNumber>{
    constructor(value: Int): this(value.toLong(), Int.SIZE_BITS)
    constructor(bitsString: String): this(bitsString.toLong(2), bitsString.length)
    constructor(bits: List<Boolean>): this(buildFromBits(bits), bits.size)

    val bits get() = this.toString().map { it == '1'}

    val indices get() =(0 until length)

    operator fun get(index: Int): Boolean = 1L.shl((length - index)) and backingField > 0

    operator fun not() = BinaryNumber("1".repeat(length).toLong(2) xor backingField)

    operator fun times(other: BinaryNumber) = BinaryNumber(backingField * other.backingField)

    override fun compareTo(other: BinaryNumber): Int = backingField.compareTo(other.backingField)

    override fun toByte(): Byte = backingField.toByte()

    override fun toChar(): Char = backingField.toChar()

    override fun toDouble(): Double = backingField.toDouble()

    override fun toFloat(): Float = backingField.toFloat()

    override fun toInt(): Int = backingField.toInt()

    override fun toLong(): Long = backingField

    override fun toShort(): Short = backingField.toShort()

    override fun toString() = backingField.toString().takeLast(length)

    fun toString(radix: Int) = backingField.toString(radix)

    companion object{
        private fun buildFromBits(bits: List<Boolean>) = bits.reversed().mapIndexed { index, b ->
            if (b) 2L.pow(index) else 0L
        }.sum()

        private fun Long.pow(power: Int): Long{
            var v: Long = 1
            repeat(power){
                v *= this
            }
            return v
        }

    }
}