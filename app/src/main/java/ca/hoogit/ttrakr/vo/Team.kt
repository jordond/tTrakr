package ca.hoogit.ttrakr.vo

data class Team(
        val abbreviation: String = "",
        val description: String = "",
        val formed: String = "",
        val name: String = "",
        val home: TeamHome = TeamHome(),
        val images: TeamImages = TeamImages(),
        val players: MutableList<Player> = mutableListOf()
)

data class TeamHome (
        val location: String = "",
        val stadium: String = "",
        val thumbnail: String = ""
)

data class TeamImages(
        val badge: String? = "",
        val jersey: String? = "",
        val logo: String? = ""
)