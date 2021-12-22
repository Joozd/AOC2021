package day19

import common.coordinates.XYZCoordinate
import common.extensions.zip

/**
 * A Scanner pings beacons. It doesn't know where it is hor how it is oriented.
 */
class Scanner(private val pingedBeacons: List<XYZCoordinate>, val name: String = "scanner XXX") {
    override fun toString() = "$name ($alignmentX, $alignmentY, $alignmentZ)"// with $alignedBeacons aligned beacons"

    // When scanner finds out where it is, enter that here
    private var alignmentX: Int? = null
    private var alignmentY: Int? = null
    private var alignmentZ: Int? = null

    fun position() = XYZCoordinate(alignmentX!!, alignmentY!!, alignmentZ!!)

    //When scanner finds out its position and orientation, place positions of beacons here
    var alignedBeacons: List<XYZCoordinate>? = null
        private set

    /**
     * Try to align this scanner to another scanner whose beacons have known positions
     */
    fun alignTo(otherScanner: Scanner): Boolean{
        val rotations = rotations()
        while(rotations.hasNext()) {
            if(tryToAlign(rotations.next(), otherScanner)) return true
        }
        return false
    }

    /**
     * Set this Scanner as baseline scanner (position 0 and correct alignment)
     */
    fun setAsBaseline(){
        alignmentX = 0
        alignmentY = 0
        alignmentZ = 0
        alignedBeacons = pingedBeacons
    }

    /**
     * Try to align this scanner to a list of beacons. If 12 or more match, this works.
     */
    private fun tryToAlign(beacons: List<XYZCoordinate>, anchor: Scanner): Boolean{
        val beaconsToAlignTo = anchor.alignedBeacons!!
        // find alignments on X-axis
        val possiblexAligns = getPossibleXalignments(beacons, beaconsToAlignTo)
        if (possiblexAligns.isEmpty()) return false

        // find alignments on Y-axis, only from those aligning on X-axis
        val possibleXYAligns = getPossibleXYAlignments(possiblexAligns, beaconsToAlignTo)
        if (possibleXYAligns.isEmpty()) return false

        // find alignments on Z-axis, only from those aligning on X- and Y-axis
        val possibleXYZAligns = getPossibleXYZAlignments(possibleXYAligns, beaconsToAlignTo)
        if (possibleXYAligns.isEmpty()) return false

        with(possibleXYZAligns.first()){
            println("Aligning $name to $anchor")
            alignmentX = xOffset!!// + anchor.alignmentX!!
            alignmentY = yOffset!!// + anchor.alignmentY!!
            alignmentZ = zOffset!!// + anchor.alignmentZ!!

            alignedBeacons = align(beacons)
        }
        return true
    }



    /**
     * Return a list of all possible aligning beacons on x-axis with their alignment distance
     */
    private fun getPossibleXalignments(beacons: List<XYZCoordinate>, beaconsToAlignTo: List<XYZCoordinate>): List<PossibleAlignment>{
        val xOffsetsLists = beacons.map{ b ->
            beaconsToAlignTo.map { b.x - it.x }
        }
        val validAlignments = findValidOffsets(xOffsetsLists)
        val validBeacons = findValidBeacons(validAlignments, beacons, xOffsetsLists)
        return validBeacons.mapIndexed { index, list -> PossibleAlignment(list, xOffset = validAlignments[index]) }
    }

    /**
     * Return a list of all possible aligning beacons on x- and y-axis with their alignment distances
     */
    private fun getPossibleXYAlignments(beacons: List<PossibleAlignment>, beaconsToAlignTo: List<XYZCoordinate>): List<PossibleAlignment> =
        beacons.mapNotNull { xMatch ->
            getPossibleYAlignments(xMatch.aligningSet, beaconsToAlignTo).takeIf{ it.isNotEmpty() }?.let{ yMatches ->
                yMatches.map{ yMatch ->
                    PossibleAlignment(yMatch.aligningSet, xOffset = xMatch.xOffset, yOffset = yMatch.yOffset )
                }
            }
        }.flatten()


    /**
     * Return a list of all possible aligning beacons on x-, y- and z-axis with their alignment distances
     */
    private fun getPossibleXYZAlignments(beacons: List<PossibleAlignment>, beaconsToAlignTo: List<XYZCoordinate>): List<PossibleAlignment> =
        beacons.mapNotNull { yMatch ->
            getPossibleZAlignments(yMatch.aligningSet, beaconsToAlignTo).takeIf{ it.isNotEmpty() }?.let{ yMatches ->
                yMatches.map{ zMatch ->
                    PossibleAlignment(yMatch.aligningSet, xOffset = yMatch.xOffset, yOffset = yMatch.yOffset, zOffset = zMatch.zOffset )
                }
            }
        }.flatten()




    /**
     * Return a list of all possible aligning beacons on x-axis with their alignment distance
     */
    private fun getPossibleYAlignments(beacons: List<XYZCoordinate>, beaconsToAlignTo: List<XYZCoordinate>): List<PossibleAlignment>{
        val yOffsetsLists = beacons.map{ b ->
            beaconsToAlignTo.map { b.y - it.y }
        }
        val validAlignments = findValidOffsets(yOffsetsLists)
        val validBeacons = findValidBeacons(validAlignments, beacons, yOffsetsLists)
        return validBeacons.mapIndexed { index, list -> PossibleAlignment(list, yOffset = validAlignments[index]) }
    }

    /**
     * Return a list of all possible aligning beacons on x-axis with their alignment distance
     */
    private fun getPossibleZAlignments(beacons: List<XYZCoordinate>, beaconsToAlignTo: List<XYZCoordinate>): List<PossibleAlignment>{
        val zOffsetsLists = beacons.map{ b ->
            beaconsToAlignTo.map { b.z - it.z }
        }
        val validAlignments = findValidOffsets(zOffsetsLists)
        val validBeacons = findValidBeacons(validAlignments, beacons, zOffsetsLists)
        return validBeacons.mapIndexed { index, list -> PossibleAlignment(list, zOffset = validAlignments[index]) }
    }

    /**
     * Find all offsets that occur on at least 12 beacons in
     */
    private fun findValidOffsets(offsetsLists: List<List<Int>>) =
        offsetsLists.flatten().distinct().filter {
                offset -> offsetsLists.count { offset in it } >= 12
        }


    /**
     * find all beacons who are part of this match, i.e. who has a valid offset in their offsets list
     */
    private fun findValidBeacons(
        validAlignments: List<Int>,
        beacons: List<XYZCoordinate>,
        offsetsList: List<List<Int>>
    ) = validAlignments.map { beacons.filterIndexed { index, _ -> it in offsetsList[index] } }

    /**
     * Align beacons with the known position of this Scanner
     */
    private fun align(beacons: List<XYZCoordinate>) = beacons.map{
        XYZCoordinate(
            it.x - alignmentX!!,
            it.y - alignmentY!!,
            it.z - alignmentZ!!
        )
    }

    /**
     * Iterate through all possible rotations of this Scanner(facing 6 sides, rotate 4 times for each side)
     */
    fun rotations(): Iterator<List<XYZCoordinate>> {
        val tops = pingedBeacons.makeTops()
        return zip((zip(tops).rotations())).iterator()
    }

    /**
     * Rotate sensor so all 6 possible sides are on top once
     * I am too dumb to do this with math.
     */
    private fun List<XYZCoordinate>.makeTops(): List<List<XYZCoordinate>> =
        listOf(
            this,
            map{ XYZCoordinate(it.z, it.x, it.y) },
            map{ XYZCoordinate(it.y, it.z, it.x) },
            map{ XYZCoordinate(it.x * -1, it.z*-1, it.y*-1) },
            map{ XYZCoordinate(it.y * -1, it.x*-1, it.z*-1) },
            map{ XYZCoordinate(it.z * -1, it.y*-1, it.x*-1) },
        )

    /**
     * Rotate a list of coordinates around this Scanner's X-axis
     */
    private fun List<List<XYZCoordinate>>.rotations(): List<List<XYZCoordinate>> =
        map {
            it.map { c ->
                listOf(
                    c,
                    XYZCoordinate(c.x, c.z*-1, c.y),
                    XYZCoordinate(c.x, c.y*-1, c.z*-1),
                    XYZCoordinate(c.x, c.z, c.y*-1)
                )
            }.flatten()
        }

    /**
     * Data class to hold possible alignments and their data
     */
    private data class PossibleAlignment(val aligningSet: List<XYZCoordinate>, val xOffset: Int? = null, val yOffset: Int? = null, val zOffset: Int? = null)

    companion object{
        /**
         * Parse a Scanner object
         */
        fun parse(text: String): Scanner{
            val lines = text.lines()
            val name = lines.first()
            val cc = lines.drop(1).map{ XYZCoordinate.parse(it)}
            return Scanner(cc, name)
        }
    }
}