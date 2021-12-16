
import day16.toBinaryString
import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val hexString = "D2FE28"
        val binString = "110100101111111000101000"
        assertEquals(binString, hexString.toBinaryString())
    }

}