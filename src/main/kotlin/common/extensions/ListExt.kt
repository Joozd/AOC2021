package common.extensions

fun <E> List<E>.printLines() = this.joinToString("\n")

fun <E> MutableList<E>.replace(element: E, replacement: E){
    val index = indexOf(element)
    if (index == -1) return
    this[index] = replacement
    // recursively do this for all elements
    if (element in this) replace(element, replacement)
}