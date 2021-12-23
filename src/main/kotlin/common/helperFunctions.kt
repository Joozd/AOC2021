package common

import common.coordinates.CoordinateWithValue

/**
 * Count the elements ina  Collection and return them as a map
 */
fun <T> countEqualElements(collection: Collection<T>) = collection.groupingBy { it }.eachCount()

fun buildCoordinatesWithValuesMap(lines: List<String>): List<CoordinateWithValue<Char>> =
    lines.mapIndexed { y, line ->
        line.mapIndexed { x, c ->
            CoordinateWithValue(x,y,c)
        }
    }.flatten()