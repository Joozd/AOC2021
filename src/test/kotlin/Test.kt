
import common.extensions.printLines
import day16.packets.LiteralValue
import day16.packets.Packet
import day16.toBinaryString
import org.junit.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun test(){
        val literalHexString = "D2FE28"
        val literalBinString = "110100101111111000101000"
        assertEquals(literalBinString, literalHexString.toBinaryString())
        val literalPacket = Packet.parseOnePacket(literalBinString)
        assertEquals("110100101111111000101".length, literalPacket.bitsUsedInConstructing)
        val lpVersion = 6
        val lpLiteralValue = 2021L
        assert (literalPacket is LiteralValue)
        assertEquals(lpVersion, literalPacket.version)
        assertEquals(lpLiteralValue, (literalPacket as LiteralValue).value)

        val tests = listOf(
            TestString("8A004A801A8002F478", 16),
            TestString("620080001611562C8802118E34", 12),
            TestString("C0015000016115A2E0802F182340", 23),
            TestString("A0016C880162017C3686B18A3D4780", 31)
        )

        tests.forEach{
            val binData = it.data.toBinaryString()
            val packets = Packet.makePackets(binData)
            println(packets.printLines() + "\n")
            assertEquals(it.sum, packets.sumOf { p -> p.sumOfVerionNumbers() })
        }
    }

    private class TestString(val data: String, val sum: Int)

}