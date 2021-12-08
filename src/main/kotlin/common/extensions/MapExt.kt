package common.extensions

fun <K,V>Map<K,V>.reverse(): Map<V,K> =
    this.map {
        it.value to it.key
    }.toMap()

