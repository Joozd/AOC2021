package common.coordinates.imaging

import common.coordinates.CoordinateWithValue
import java.awt.AlphaComposite
import java.awt.image.BufferedImage

class GifSequenceBuilder(private val delay: Int, private val loop: Boolean) {
    private val frames = ArrayList<BufferedImage>()

    private fun width() = frames.maxOf { it.width }
    private fun height() = frames.maxOf { it.height }

    fun addFrame(frame: BufferedImage){
        frames.add(frame)
    }

    fun <T> addCoordinates(coordinates: List<CoordinateWithValue<T>>, scale: Int = 3, colorMaker: PNGMap.ColorMaker<T>){
        addFrame(PNGMap(coordinates, scale, colorMaker).makeBufferedImage())
    }

    fun writeGif(fileName: String){
        if (frames.isEmpty()) error ("Cannot build an empty gif")
        GifSequenceWriter(fileName, frames.first().type, delay, loop).use{ writer ->
            frames.forEach {
                writer.writeToSequence(prepareFrame(it))
            }
        }
    }


    private fun prepareFrame(frame: BufferedImage): BufferedImage{
        val w = width()
        val h = height()
        if (frame.width == w && frame.height == h)
            return frame

        val xOffset = (w - frame.width) / 2
        val yOffset = (h - frame.height) / 2
        val newFrame = BufferedImage(w,h,frame.type)
        newFrame.createGraphics().apply{
            composite = AlphaComposite.Src
            drawImage(frame,
                xOffset,
                yOffset,
                null
            )
            dispose()
        }
        return newFrame
    }
}