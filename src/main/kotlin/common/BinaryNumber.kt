package common

class BinaryNumber(private val bits: List<Boolean>): Number(), Comparable<BinaryNumber> {
    constructor(bitsString: String): this(bitsString.map { it == '1' })
    constructor(value: Long): this(value.toString(2).padStart(Long.SIZE_BITS, '0'))
    constructor(value: Int): this(value.toString(2).padStart(Int.SIZE_BITS, '0'))
    constructor(value: Short): this(value.toString(2).padStart(Short.SIZE_BITS, '0'))

    override fun toByte(): Byte = getValue().toByte()

    override fun toChar(): Char = getValue().toInt().toChar()

    override fun toDouble(): Double = getValue().toDouble()

    override fun toFloat(): Float = getValue().toFloat()

    override fun toInt(): Int = getValue().toInt()

    override fun toLong(): Long = getValue()

    override fun toShort(): Short = getValue().toShort()

    override fun toString(): String = bits.map { if (it) '1' else '0' }.joinToString("")

     fun toString(radix: Int): String = when(radix){
         2 -> this.toString()
         else -> getValue().toString(radix)
    }

    override fun compareTo(other: BinaryNumber): Int = getValue().compareTo(other.toLong())

    fun getOrNull(index:Int) = if (index in bits.indices) bits[index] else null

    operator fun get(index: Int) = bits[index]

    operator fun not(): BinaryNumber = BinaryNumber(bits.map {!it})

    operator fun times(other: Number) = BinaryNumber(getValue() * other.toLong())

    infix fun and(other: BinaryNumber): BinaryNumber {
        val size = maxOf(this.bits.size, other.bits.size)
        return BinaryNumber((0 until size).map {
            (this.getOrNull(it) ?: false) and (other.getOrNull(it) ?: false)
        })
    }

    infix fun or(other: BinaryNumber): BinaryNumber {
        val size = maxOf(this.bits.size, other.bits.size)
        return BinaryNumber((0 until size).map {
            (this.getOrNull(it) ?: false) or (other.getOrNull(it) ?: false)
        })
    }

    infix fun xor(other: BinaryNumber): BinaryNumber {
        val size = maxOf(this.bits.size, other.bits.size)
        return BinaryNumber((0 until size).map {
            (this.getOrNull(it) ?: false) xor (other.getOrNull(it) ?: false)
        })
    }


    val indices = bits.indices


    private fun getValue(): Long{
        var v: Long = 0
        bits.reversed().forEachIndexed { index, b ->
            if (b)
                v += 2L.pow(index)
        }
        return v
    }

    private fun Long.pow(power: Int): Long{
        var v: Long = 1
        repeat(power){
            v *= this
        }
        return v
    }
}