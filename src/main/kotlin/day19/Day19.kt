package day19

import common.Solution
import common.extensions.forEachMultyThreaded
import java.time.Instant

/**
 * Day 19: Align beacons. Did this wile tired. Not too fast (takes 3000ms or so, probably because of lots of looking for matches)
 * May speed it up if I feel like ti, for now made some work multithreaded which slashed time in half.
 */
class Day19: Solution {
    override val day = 19

    private val input = inputLines()
    private val scanners = input.joinToString("\n").split("\n\n").map{Scanner.parse(it)}

    override fun answer1(): Any{
        val alignedScanners = ArrayList<Scanner>(scanners.size)
        val unalignedScanners = ArrayList(scanners)
        alignedScanners.add(unalignedScanners.removeFirst().apply{
            setAsBaseline()
        })
        while(unalignedScanners.isNotEmpty()){
            //val alignedScannersSnapshot = alignedScanners.toList()

            unalignedScanners.toList().forEachMultyThreaded { s -> // doing this multithreaded makes it about twice as fast (3000ms instead of 6000)
                if (alignedScanners.toList().any { aligned -> s.alignTo(aligned) }){
                    unalignedScanners.remove(s)
                    alignedScanners.add(s)
                }
            }
        }

        return scanners.map {it.alignedBeacons!!}.flatten().distinct().size
    }

    override fun answer2(): Any? = scanners.map{ scanner ->
            scanners.maxOf { other -> scanner.position().distanceTo(other.position()) }
        }.maxOrNull()
}