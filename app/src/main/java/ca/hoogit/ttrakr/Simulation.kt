package ca.hoogit.ttrakr


data class Simulation(
        val chance: Int = 5,
        val factor: Int = 1,
        val maxGames: Int = -1,
        val startRange: Int = 500,
        val started: Boolean = false,
        var start: String? = ""
)