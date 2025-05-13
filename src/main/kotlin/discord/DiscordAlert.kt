package org.example.discord

import com.google.gson.Gson
import org.example.ConfigurableConstants.DISCORD_WEBHOOK_URL
import org.example.network.Player
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class DiscordAlert {

    private val messageBuilder = DiscordMessageBuilder()

    fun sendWinnerAlert(
        winnerName: String,
        playersList: List<Player>,
        mapName: String
    ) {
        sendAlert(
            messageBuilder.getWinnerMessage(
                winnerName = winnerName,
                playersList = playersList,
                mapName = mapName
            )
        )
    }

    fun sendPlayersInServerAlert(playersList: List<Player>) {
        sendAlert(messageBuilder.getPlayersInServerMessage(playersList))
    }

    private fun sendAlert(message: DiscordMessage) {
        URL(DISCORD_WEBHOOK_URL).asHttpUrlConnection().apply {
            sendRequest(Gson().toJson(message))
            responseCode
            disconnect()
        }
    }

    private fun URL.asHttpUrlConnection() = (openConnection() as HttpURLConnection).apply {
        requestMethod = "POST"
        setRequestProperty("Content-Type", "application/json")
        doOutput = true
    }

    private fun HttpURLConnection.sendRequest(payload: String) {
        outputStream.use { outputStream ->
            OutputStreamWriter(
                outputStream,
                Charsets.UTF_8
            ).use { writer ->
                writer.write(payload)
                writer.flush()
            }
        }
    }
}
