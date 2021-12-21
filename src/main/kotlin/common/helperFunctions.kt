package common

/**
 * Count the elements ina  Collection and return them as a map
 */
fun <T> countEqualElements(collection: Collection<T>) = collection.groupingBy { it }.eachCount()