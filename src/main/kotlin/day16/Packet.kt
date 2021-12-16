package day16

abstract class Packet protected constructor(val version: Int, val bitsUsedInConstructing: Int){
    abstract fun sumOfVerionNumbers(): Int
    companion object{
        fun parse(input: String): Packet =
            when (input.getID()){
                ID_LITERAL_VALUE -> LiteralValue.make(input)
                else -> TODO("WIP")

            }

        const val ID_LITERAL_VALUE = 4
    }
}