package org.example.ut

class UnrealTournamentRepository {

    private val dataSource = UnrealTournamentDataSource()

    fun getServer(): Server {
        val serverRaw = dataSource.getServerRaw()

        val mapNameIndex = serverRaw.indexOf("mapname")
        val fragLimitIndex = serverRaw.indexOf("fraglimit")
        val gameTypeIndex = serverRaw.indexOf("gametype")

        return Server(
            mapName = serverRaw[mapNameIndex + 1],
            fragLimit = serverRaw[fragLimitIndex + 1],
            isDeathMatch = serverRaw[gameTypeIndex + 1] == "DeathMatchPlus",
        )
    }

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
