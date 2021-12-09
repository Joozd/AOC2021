package common.extensions

fun <K, V>MutableMap<K,V>.removeAllKeys(keys: Collection<K>){
    keys.forEach {
        remove(it)
    }
}