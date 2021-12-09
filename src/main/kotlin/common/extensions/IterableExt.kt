package common.extensions

fun Iterable<Int?>.sumNotNull(): Int {
    var sum = 0
    for (element in this) {
        sum += element ?: 0
    }
    return sum
}

fun Iterable<Int>.product() = reduce { acc, i -> acc * i }