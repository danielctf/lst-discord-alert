package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class AlertHandler {

    private val serverRepository = ServerRepository()
    private val playersRepository = PlayersRepository()
    private val discordAlert = DiscordAlert()

    private var lastAlertSent = 0L
    private var canAlertWinner = true

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
        val server = serverRepository.getServer()
        val playersList = playersRepository.getPlayersList()

        sendPlayersInServerAlert(playersList)
        sendWinnerAlert(playersList, server)
    }

    private fun sendPlayersInServerAlert(playersList: List<Player>) {
        if (playersList.size >= 2 && getCurrentTime() - lastAlertSent >= MIN_TIME_BETWEEN_ALERTS) {
            lastAlertSent = getCurrentTime()
            discordAlert.sendPlayersInServerAlert(playersList)
        }
    }

    private fun getCurrentTime() = System.currentTimeMillis()

    private fun sendWinnerAlert(playersList: List<Player>, server: Server) {
        if (!canAlertWinner(playersList, server)) {
            return
        }
        playersList.find { it.frags == server.fragLimit }?.let { winner ->
            lastAlertSent = getCurrentTime()
            canAlertWinner = false
            discordAlert.sendWinnerAlert(
                winner = winner,
                playersList = playersList,
                mapName = server.mapName
            )
        }
    }

    private fun canAlertWinner(playersList: List<Player>, server: Server): Boolean {
        if (!playersList.any { it.frags == server.fragLimit }) {
            canAlertWinner = true
        }
        return canAlertWinner && server.isDeathMatch
    }

    private companion object {
        // 20 minutes
        const val MIN_TIME_BETWEEN_ALERTS = 20 * 60 * 1000
    }
}
