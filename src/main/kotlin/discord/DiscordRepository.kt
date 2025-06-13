package org.agroapp.discord

import org.agroapp.ut.Player
import org.agroapp.ut.Server

class DiscordRepository {

    private val dataSource = DiscordDataSource()
    private val messageGenerator = DiscordMessageGenerator()

    fun sendWinnerAlert(
        winner: Player,
        playersList: List<Player>,
        server: Server
    ) {
        dataSource.sendAlert(
            messageGenerator.generateWinnerMessage(
                winner = winner,
                playersList = playersList,
                server = server
            )
        )
    }

    fun sendPlayersAlert(playersList: List<Player>) {
        dataSource.sendAlert(
            messageGenerator.generatePlayersMessage(playersList)
        )
    }
}
