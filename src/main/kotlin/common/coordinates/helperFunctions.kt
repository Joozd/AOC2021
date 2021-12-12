package common.coordinates

import common.extensions.printLines

fun Collection<CoordinateWithValue<*>>.drawMap(): String{
    val dimensions = DrawingArea(this)
    val map = Array(dimensions.height + 1) { y ->
        CharArray(dimensions.width + 1) { x ->
            this.firstOrNull { it == Coordinate(x,y) }?.value?.toString()?.first() ?: ' '
        }
    }
    return map.joinToString("\n") { String(it) }
}

private class DrawingArea(map: Collection<CoordinateWithValue<*>>){
    val maxX = map.maxOf { it.x }
    val maxY = map.maxOf { it.y }
    val minX = map.minOf { it.x }
    val minY = map.minOf { it.y }
    val width = maxX - minX
    val offsetX = minX * -1
    val height = maxY - minY
    val offsetY = minY * -1
}