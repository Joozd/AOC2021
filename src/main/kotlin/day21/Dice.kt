package day21

class Dice {
    //lastRoll starts at 4 because dice starts at 6.
    private var lastRoll: Int = 4

    val rolls get() = (lastRoll - 4) * 3

    fun roll() = 10 - lastRoll++ % 10
}