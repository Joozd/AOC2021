
import day16.LiteralValue
import day16.Packet
import day16.toBinaryString
import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val hexString = "D2FE28"
        val binString = "110100101111111000101000"
        assertEquals(binString, hexString.toBinaryString())
        val packet = Packet.parse(binString)
        val pVersion = 6
        val pLiteralValue = 2021L
        assert (packet is LiteralValue)
        assertEquals(pVersion, packet.version)
        assertEquals(pLiteralValue, (packet as LiteralValue).value)
    }

}