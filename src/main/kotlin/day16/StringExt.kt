package day16


fun String.toBinaryString(): String =
    this.map{ it.digitToInt(16).toString(2).padStart(4, '0')}.joinToString("")

fun String.getVersion() = take(3).toInt(2)

fun String.getID() = substring(3..5).toInt(2)
