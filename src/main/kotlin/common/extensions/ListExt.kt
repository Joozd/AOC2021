package common.extensions

/**
 * Prints all elements on this list on a new line
 */
fun <E> List<E>.printLines() = this.joinToString("\n")


/**
 * Replace all elements [element] in this list with [replacement] in place
 */
fun <E> MutableList<E>.replace(element: E, replacement: E){
    val index = indexOf(element)
    if (index == -1) return
    this[index] = replacement
    // recursively do this for all elements
    if (element in this) replace(element, replacement)
}

/**
 * How many elements are duplicates?
 * It doesn't matter how many occurrences, [x,x,x,x,x] will give "1"
 */
fun <T> List<T>.countDuplicates(): Int =
    size - this.toSet().size

operator fun <T> List<T>.times(n: Int): List<T>{
    val result = ArrayList<T>(size*n)
    repeat(n){
        result.addAll(this)
    }
    return result
}
