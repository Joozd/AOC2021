package common.extensions

import kotlinx.coroutines.*

/**
 * Prints all elements on this list on a new line
 */
fun <E> Iterable<E>.printLines() = this.joinToString("\n")


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

fun <T> zip(listsToZip: Collection<List<T>>): List<List<T>>{
    val minSize = listsToZip.minOfOrNull{ it.size } ?: return emptyList()
    val lists = Array(minSize){ i ->
        listsToZip.map{ l-> l[i] }
    }
    return lists.toList()
}


fun <T, O> List<T>.mapNotNullMultiThreaded( action: (T) -> O?): List<O> =
    mapMultiThreaded(action).filterNotNull()

operator fun <T> List<T>.times(n: Int): List<T>{
    val result = ArrayList<T>(size*n)
    repeat(n){
        result.addAll(this)
    }
    return result
}
