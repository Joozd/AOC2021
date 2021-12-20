package common.extensions

fun <K,V>Map<K,V>.reverse(): Map<V,K> =
    this.map {
        it.value to it.key
    }.toMap()

/**
 * Creates a new read-only map by replacing or adding entries to this map from another [map].
 *
 * The returned map preserves the entry iteration order of the original map.
 * Those entries of another [map] that are missing in this map are iterated in the end in the order of that [map].
 */
operator fun <K, V> Map<out K, V>.plus(map: Map<out K, V>): Map<K, V> =
    LinkedHashMap(this).apply { putAll(map) }

