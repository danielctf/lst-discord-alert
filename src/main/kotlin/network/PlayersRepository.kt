package org.example.network

class PlayersRepository {

    private val dataSource = CommonDataSource()

    fun getPlayersList(): List<Player> {
        val playersRaw = dataSource.getPlayersRaw()

        val playersList = mutableListOf<Player>()
        for (i in 1 until (playersRaw.size - 4) step 16) {
            playersList.add(
                Player(
                    name = playersRaw[i + 1].trim(),
                    frags = playersRaw[i + 3].trim(),
                    ping = playersRaw[i + 5].trim()
                )
            )
        }

        return playersList
    }
}
