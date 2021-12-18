package day18

/**
 * This holds either a SlugFishNumber or a real number
 */
interface SlugFishData {
    operator fun invoke(): Long

    /**
     * Only to be used on pre-split lines as it only supports single-digit numbers
     */
    companion object{
        fun parse(line: Iterator<Char>): SlugFishData{
            val firstChar = line.next()
            if (firstChar.isDigit()) return SlugFishDigit(firstChar)
            val input = StringBuilder()
            var depth = 1
            while (depth > 0){
                val c = line.next()
                when (c) {
                    '[' -> depth++
                    ']' -> depth--
                }
                if (depth > 0)input.append(c)
            }
            //println("Input is $input")
            val childrenIterator = input.iterator()
            val left = parse(childrenIterator)
            require(childrenIterator.next() == ',')
            val right = parse(childrenIterator)
            return SlugFishPair(left, right)
        }
    }
}