package common.extensions

fun <E> List<E>.printLines() = this.joinToString("\n")

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
fun <T> List<T>.countDuplicates(): Int{
    val countedElements = HashMap<T, Int>()
    forEach{ e ->
        countedElements[e] = (countedElements[e] ?: 0) + 1
    }
    return countedElements.values.count { it > 1}
}