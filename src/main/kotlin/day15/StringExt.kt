package day15

internal fun String.repeated5TimesIncreasing()
        = Array(5){ i -> increaseAllDigitsByAndMod10Min1(i) }
    .joinToString("")



fun String.increaseAllDigitsByAndMod10Min1(n: Int) =
    map{ c-> c.increaseByAndMod10MinimumOne(n)}.joinToString("")

