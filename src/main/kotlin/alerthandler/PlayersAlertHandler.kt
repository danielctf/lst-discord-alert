package org.agroapp.alerthandler

import org.agroapp.discord.DiscordRepository
import org.agroapp.ut.Player

class PlayersAlertHandler(
    private val handler: LastAlertSentHandler
) {

    private val repository = DiscordRepository()

    fun sendPlayersAlert(playersList: List<Player>) {
        if (!canSendPlayersAlert(playersList)) {
            return
        }
        handler.refreshLastAlertSent()
        repository.sendPlayersAlert(playersList)
    }

    private fun canSendPlayersAlert(playersList: List<Player>): Boolean =
        handler.getLastAlertSentElapsedTime() >= MIN_TIME_BETWEEN_ALERTS && playersList.size >= 2

    private companion object {
        // 20 minutes
        const val MIN_TIME_BETWEEN_ALERTS = 20 * 60 * 1000
    }
}
