package common.extensions

/**
 * Absolute value of [this]
 */
fun Int.abs() = if (this >= 0) this else this * -1

/**
 * [this] to the [n]th power
 */
fun Int.pow(n: Int): Int{
    var v = 1
    repeat(n){
        v *= this
    }
    return v
}