import common.Coordinate
import common.Line
import common.extensions.grabInts
import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val c1 = Coordinate(1,1)
        val c2 = Coordinate(1,5)
        val c3 = Coordinate(5,1)

        val t1 = Coordinate(1,3)
        val t2 = Coordinate(3,1)

        val l1 = Line(c1, c2)
        val l1b = Line(c2, c1)

        val l2 = Line(c1,c3)
        val l2b = Line (c3, c1)

        println(l1.toList())
        println(l2.toList())

        assert (t1 in l1)
        assert(t1 in l1b)

        assert(t2 in l2)
        assert (t2 in l2b)
    }

    @Test
    fun testIntGrabber(){
        val line = "1,2,3 4   56 and also- -78 - 9"
        val expected = listOf(1,2,3,4,56,-78,9)
        assertEquals(expected, line.grabInts())
    }

}