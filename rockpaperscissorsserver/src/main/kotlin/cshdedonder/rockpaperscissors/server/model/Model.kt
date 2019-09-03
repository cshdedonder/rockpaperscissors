package cshdedonder.rockpaperscissors.server.model

import cshdedonder.rockpaperscissors.protocol.Action
import cshdedonder.rockpaperscissors.protocol.Result

object Model {

    private val games: MutableList<Game> = ArrayList()

    fun joinGame(opponent: String, player: String, action: Action): Game? {
        val game = games.find { it.turn1.name == opponent && it.turn2 ==  null}
        game?.turn2 = Turn(player, action)
        return game
    }

    fun createGame(player: String, action: Action): Game = Game(Turn(player, action)).also { games.add(it) }
}