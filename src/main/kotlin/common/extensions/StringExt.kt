package common.extensions

import java.lang.Exception

/**
 * Split a String into words (by spaces or tabs)
 */
fun String.words() = this.split("""\s+""".toRegex())

/**
 * Grab all Ints in this line. Anything that is not a digit or a minus sign marks a new Int
 * eg. "1,2,3 4 56 and also- -78 - 9" will become [1,2,3,4,56,-78,9]
 */
fun String.grabInts(): List<Int> =
    this.replace("[^-0-9]".toRegex(), " ")
        .split(" ")
        .filter { it.isNotBlank()}
        .mapNotNull  { try { it.toInt() } catch (e: Exception){ null } }

fun String.sortString() = this.toList().sorted().joinToString("")