package org.example.discord

import org.example.ut.Player

class DiscordAlert {

    private val dataSource = DiscordDataSource()
    private val messageBuilder = DiscordMessageBuilder()

    fun sendWinnerAlert(
        winnerName: String,
        playersList: List<Player>,
        mapName: String
    ) {
        dataSource.sendAlert(
            messageBuilder.getWinnerMessage(
                winnerName = winnerName,
                playersList = playersList,
                mapName = mapName
            )
        )
    }

    fun sendPlayersInServerAlert(playersList: List<Player>) {
        dataSource.sendAlert(
            messageBuilder.getPlayersInServerMessage(playersList)
        )
    }
}
