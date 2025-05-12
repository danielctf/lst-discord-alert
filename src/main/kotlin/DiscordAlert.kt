package org.example

import com.google.gson.Gson
import org.example.ConfigurableConstants.DISCORD_WEBHOOK_URL
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class DiscordAlert {

    fun sendWinnerAlert(
        winner: Player,
        playersList: List<Player>,
        mapName: String
    ) {
        val sortedPlayersList = playersList.sortedByDescending { it.frags.toIntOrNull() }
        sendAlert(
            DiscordMessage(
                embeds = listOf(
                    Embed(
                        title = "\uD83C\uDFC6 ${winner.name} wins the match in $mapName !",
                        color = 16766720,
                        fields = listOf(
                            Field(
                                name = "Player",
                                value = sortedPlayersList.joinToString("\n") { it.name }
                            ),
                            Field(
                                name = "Frags",
                                value = sortedPlayersList.joinToString("\n") { it.frags }
                            ),
                            Field(
                                name = "Ping",
                                value = sortedPlayersList.joinToString("\n") { it.ping }
                            )
                        )
                    )
                )
            )
        )
    }

    fun sendPlayersInServerAlert(playersList: List<Player>) {
        val playersString = playersList.joinToString(" and ") { it.name }
        sendAlert(
            DiscordMessage(
                embeds = listOf(
                    Embed(
                        description = "\uD83D\uDC65 $playersString have joined the LST server !",
                        color = 3447003
                    )
                ),
            )
        )
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
