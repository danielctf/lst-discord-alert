package org.example.network

import org.example.ConfigurableConstants.IP
import org.example.ConfigurableConstants.PORT
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class CommonDataSource {

    private val status = "\\status\\"
    private val players = "\\players\\"

    private val map = mapOf(
        status to status.toByteArray(),
        players to players.toByteArray(),
    )

    fun getServerRaw(): List<String> = getRaw(status)

    fun getPlayersRaw(): List<String> = getRaw(players)

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
        // Save some computing time by already having the byte array
        setData(map[type])
    }

    private fun getSocket(packet: DatagramPacket) = DatagramSocket().apply {
        soTimeout = 5000
        send(packet)
    }

    private fun parseServerResponse(byteArray: ByteArray, packetLength: Int): List<String> =
        String(byteArray, 0, packetLength).split("\\")
}
