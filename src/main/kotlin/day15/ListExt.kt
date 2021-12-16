package day15

internal fun List<String>.repeated5TimesIncreasing()
        = Array(5){ i -> map { it.increaseAllDigitsByAndMod10Min1(i) }}.toList().flatten()

internal fun List<String>.timesFiveIncreased(): List<String> =
    map { it.repeated5TimesIncreasing()}
        .repeated5TimesIncreasing()