package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.example.discord.DiscordAlert
import org.example.ut.Player
import org.example.ut.Server
import org.example.ut.UnrealTournamentRepository

class AlertHandler {

    private val repository = UnrealTournamentRepository()
    private val discordAlert = DiscordAlert()

    private var lastAlertSent = 0L
    private var canSendWinnerAlert = true

    fun start() {
        runBlocking {
            println("Started")
            while (true) {
                try {
                    sendAlerts()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(5000)
            }
        }
    }

    private fun sendAlerts() {
        val server = repository.getServer()
        val playersList = repository.getPlayersList()

        sendPlayersInServerAlert(playersList)
        sendWinnerAlert(playersList, server)
    }

    private fun sendPlayersInServerAlert(playersList: List<Player>) {
        if (!canSendPlayersInServerAlert(playersList)) {
            return
        }
        lastAlertSent = getCurrentTime()
        discordAlert.sendPlayersInServerAlert(playersList)
    }

    private fun canSendPlayersInServerAlert(playersList: List<Player>): Boolean =
        getCurrentTime() - lastAlertSent >= MIN_TIME_BETWEEN_ALERTS && playersList.size >= 2

    private fun getCurrentTime(): Long = System.currentTimeMillis()

    private fun sendWinnerAlert(playersList: List<Player>, server: Server) {
        if (!canSendWinnerAlert(playersList, server)) {
            return
        }
        playersList.find { it.frags == server.fragLimit }?.let { winner ->
            lastAlertSent = getCurrentTime()
            canSendWinnerAlert = false
            discordAlert.sendWinnerAlert(
                winnerName = winner.name,
                playersList = playersList,
                mapName = server.mapName
            )
        }
    }

    private fun canSendWinnerAlert(playersList: List<Player>, server: Server): Boolean {
        if (!playersList.any { it.frags == server.fragLimit }) {
            canSendWinnerAlert = true
        }
        return canSendWinnerAlert && server.isDeathMatch
    }

    private companion object {
        // 20 minutes
        const val MIN_TIME_BETWEEN_ALERTS = 20 * 60 * 1000
    }
}
