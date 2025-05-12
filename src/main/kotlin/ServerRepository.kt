package org.example

class ServerRepository {

    private val dataSource = CommonDataSource()

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
}
