import common.Coordinate
import common.Line
import common.extensions.grabInts
import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val t1 = "abcde"
        val tp = "ebd"
        val tf = "def"
        assert(t1.holdsAllLettersOf(tp))
        assert(!t1.holdsAllLettersOf(tf))
    }

    fun String.holdsAllLettersOf(other: String) = other.all{ it in this }
}