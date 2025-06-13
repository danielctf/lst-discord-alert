package org.agroapp.alerthandler

import org.agroapp.discord.DiscordRepository
import org.agroapp.ut.Player
import org.agroapp.ut.Server

class WinnerAlertHandler(
    private val handler: LastAlertSentHandler
) {

    private val repository = DiscordRepository()
    private var canSendWinnerAlert = true

    fun sendWinnerAlert(playersList: List<Player>, server: Server) {
        if (!canSendWinnerAlert(playersList, server)) {
            return
        }
        playersList.find { it.frags == server.fragLimit }?.let { winner ->
            handler.refreshLastAlertSent()
            canSendWinnerAlert = false
            repository.sendWinnerAlert(
                winner = winner,
                playersList = playersList,
                server = server
            )
        }
    }

    private fun canSendWinnerAlert(playersList: List<Player>, server: Server): Boolean {
        if (!canSendWinnerAlert && !playersList.any { it.frags == server.fragLimit }) {
            canSendWinnerAlert = true
        }
        return canSendWinnerAlert && server.isDeathMatch
    }
}
