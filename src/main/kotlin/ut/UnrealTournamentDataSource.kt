package org.agroapp.ut

import org.agroapp.main.ServerConstants.IP
import org.agroapp.main.ServerConstants.PORT
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UnrealTournamentDataSource {

    private val map = mapOf(
        STATUS to STATUS.toByteArray(),
        PLAYERS to PLAYERS.toByteArray()
    )

    fun getServerRaw(): List<String> = getRaw(STATUS)

    fun getPlayersRaw(): List<String> = getRaw(PLAYERS)

    private fun getRaw(type: String): List<String> {
        val packet = getPacket(type)
        val socket = getSocket(packet)

        val byteArray = ByteArray(2048)
        packet.setData(byteArray)
        socket.receive(packet)
        socket.close()

        return parseServerResponse(
            byteArray,
            packet.length
        )
    }

    private fun getPacket(type: String) = DatagramPacket(
        byteArrayOf(1),
        1,
        InetAddress.getByName(IP),
        PORT
    ).apply {
        setData(map[type])
    }

    private fun getSocket(packet: DatagramPacket) = DatagramSocket().apply {
        soTimeout = 5000
        send(packet)
    }

    private fun parseServerResponse(byteArray: ByteArray, packetLength: Int): List<String> =
        String(byteArray, 0, packetLength).split("\\")

    private companion object {
        const val STATUS = "\\status\\"
        const val PLAYERS = "\\players\\"
    }
}
