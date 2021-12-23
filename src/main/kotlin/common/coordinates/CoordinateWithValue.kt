package common.coordinates

open class CoordinateWithValue<T>(x: Int, y: Int, var value: T): Coordinate(x,y){
    override fun toString() = "($x,$y):$value"
    fun copy(value: T = this.value) = CoordinateWithValue(x,y,value)


    constructor(coordinate: Coordinate, value: T): this(coordinate.x, coordinate.y, value)
}