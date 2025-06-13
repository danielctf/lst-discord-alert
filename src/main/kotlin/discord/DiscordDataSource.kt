package org.agroapp.discord

import com.google.gson.Gson
import org.agroapp.main.ServerConstants.DISCORD_WEBHOOK_URL
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class DiscordDataSource {

    fun sendAlert(message: DiscordMessage) {
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
