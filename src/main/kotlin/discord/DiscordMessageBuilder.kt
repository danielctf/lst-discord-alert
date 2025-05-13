package org.example.discord

import org.example.ut.Player

class DiscordMessageBuilder {

    fun getWinnerMessage(
        winnerName: String,
        playersList: List<Player>,
        mapName: String
    ): DiscordMessage {
        val sortedPlayersList = playersList.sortedByDescending { it.frags.toIntOrNull() }
        return DiscordMessage(
            embeds = listOf(
                Embed(
                    title = "\uD83C\uDFC6 $winnerName wins the match in $mapName !",
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
    }

    fun getPlayersInServerMessage(playersList: List<Player>): DiscordMessage {
        val playersString = playersList.joinToString(" and ") { it.name }
        return DiscordMessage(
            embeds = listOf(
                Embed(
                    description = "\uD83D\uDC65 $playersString have joined the LST server !",
                    color = 3447003
                )
            )
        )
    }
}
