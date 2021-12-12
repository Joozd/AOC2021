package day11

import common.coordinates.CoordinateWithValue

class Octopus(x: Int, y: Int, energy: Int): CoordinateWithValue<Int>(x,y,energy) {
    var energy get() = value
        set(it) { value = it}
}