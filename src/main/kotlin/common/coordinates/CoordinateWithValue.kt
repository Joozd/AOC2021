package common.coordinates

open class CoordinateWithValue<T>(x: Int, y: Int, var value: T): Coordinate(x,y){
    constructor(coordinate: Coordinate, value: T): this(coordinate.x, coordinate.y, value)
}