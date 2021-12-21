package common.extensions

import kotlinx.coroutines.*

fun Iterable<Int?>.sumNotNull(): Int {
    var sum = 0
    for (element in this) {
        sum += element ?: 0
    }
    return sum
}

fun Iterable<Int>.product() = reduce { acc, i -> acc * i }
fun Iterable<Long>.product() = reduce { acc, i -> acc * i }

fun <T> Iterable<T>.forEachMultyThreaded( action: (T) -> Unit){
    val coroutineContext = CoroutineScope(Dispatchers.Default)
    val results = this.map{
        coroutineContext.async { action(it) }
    }
    runBlocking {
        results.awaitAll()
    }

}