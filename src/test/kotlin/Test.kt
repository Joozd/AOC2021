import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        assertEquals(16, 2L.pow(4) )
    }

    private fun Long.pow(power: Int): Long{
        var v: Long = 1
        repeat(power){
            v *= this
        }
        return v
    }
}