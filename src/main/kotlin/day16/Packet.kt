package day16

abstract class Packet protected constructor(val version: Int, val bitsUsedInConstructing: Int){
    abstract fun sumOfVerionNumbers(): Int
    companion object{
        const val HEADER_LENGTH = 6

        const val ID_LITERAL_VALUE = 4
    }
}