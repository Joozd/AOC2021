package day18

/**
 * This holds a SlugFishNumber consisting or a pair of Slugfish- and/or real numbers
 */
class SlugFishPair(val left: SlugFishData, val right: SlugFishData): SlugFishData {
    override operator fun invoke(): Long =
        left()*3 + right() * 2


}