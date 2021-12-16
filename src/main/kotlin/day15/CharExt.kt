package day15

internal fun Char.increaseByAndMod10MinimumOne(n: Int): Char{
    val r = (this.digitToInt() + n)
    val result = if (r < 10) r else r - 9
    return '0' + result
}