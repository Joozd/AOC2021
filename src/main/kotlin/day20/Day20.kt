package day20

import common.Solution
import common.coordinates.imaging.GifSequenceBuilder

class Day20: Solution {
    override val day = 20
    private val inputLines = inputLines()
    // The image we will enhance
    private lateinit var image: EnhanceableImage

    // This not needed, only for fun
    private val gifMaker = GifSequenceBuilder(100, true)

    override fun prepare() {
        image = EnhanceableImage.make(inputLines)
    }

    /**
     * Count white pixels after 2 steps
     */
    override fun answer1(): Any {
        repeat(2) {
            //if (it%2 == 0)                                      // This not needed, only for fun
            //addFrameToGif()                                     // this not needed, only for fun
            image.enhance()
        }
        return image.countLitPixels()
    }

    /**
     * Count white pixels after another 48 steps
     */
    override fun answer2(): Any{
        repeat(48){
            //if (it%2 == 0)                                      // this not needed, only for fun
            //addFrameToGif()                                     // this not needed, only for fun
            image.enhance()
        }
        //addFrameToGif()                                         // this not needed, only for fun
        //gifMaker.writeGif("C:\\temp\\aoc\\day20.gif")           // this not needed, only for fun
        return image.countLitPixels()
    }

    /**
     * Add the most recent state of [image] to [gifMaker]
     */
    private fun addFrameToGif() {
        gifMaker.addCoordinates(image.coordinates(), scale = 3) {
            if (it.value == '#') 0xFFFFFF else 0x000000
        }
    }
}