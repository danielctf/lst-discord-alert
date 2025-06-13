package org.agroapp.alerthandler

import org.agroapp.ut.UnrealTournamentRepository

class AlertHandler {

    private val repository = UnrealTournamentRepository()
    private val lastAlertSentHandler = LastAlertSentHandler()
    private val playersAlertHandler = PlayersAlertHandler(lastAlertSentHandler)
    private val winnerAlertHandler = WinnerAlertHandler(lastAlertSentHandler)

    fun sendAlerts() {
        val server = repository.getServer()
        val playersList = repository.getPlayersList()

        playersAlertHandler.sendPlayersAlert(playersList)
        winnerAlertHandler.sendWinnerAlert(playersList, server)
    }
}
