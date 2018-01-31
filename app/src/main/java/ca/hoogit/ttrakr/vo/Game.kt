package ca.hoogit.ttrakr.vo

data class Game(
        val home: String = "",
        val away: String = "",
        val startTime: String = "",
        val details: GameDetails = GameDetails()
)

data class GameDetails(
        val active: Boolean = false,
        val finished: Boolean = false,
        val nextEventTime: String = "",
        val period: Int = 1,
        val periodEnd: String = "",
        val winner: String = "",
        val goals: MutableList<GameDetailsEvent>? = mutableListOf(),
        val penalties: MutableList<GameDetailsEvent>? = mutableListOf(),
        var score: GameDetailsScore = GameDetailsScore()
)

data class GameDetailsEvent(
        val player: String = "",
        val team: String = "",
        val assist: MutableList<String>? = mutableListOf()
)

data class GameDetailsScore(
        val away: Int = 0,
        val home: Int = 0
)
