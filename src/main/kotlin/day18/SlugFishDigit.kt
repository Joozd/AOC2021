package day18

/**
 * This consists of a real number
 */
class SlugFishDigit(private val digit: Char): SlugFishData {
    override operator fun invoke() = digit.digitToInt().toLong()
}