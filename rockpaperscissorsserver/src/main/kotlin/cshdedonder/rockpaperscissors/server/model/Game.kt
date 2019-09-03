package cshdedonder.rockpaperscissors.server.model

import cshdedonder.rockpaperscissors.protocol.Action
import cshdedonder.rockpaperscissors.protocol.Result

data class Turn(val name: String, val action: Action)

class Game(val turn1: Turn) {

    var turn2: Turn? = null
        set(value) {
            field = value
            onGameFinished?.invoke(this)
        }
    val resultPlayer1: Result?
        get() = turn2?.let {
            turn1.action.against(it.action)
        }
    val resultPlayer2: Result?
        get() = turn2?.action?.against(turn1.action)
    val isFinished: Boolean
        get() = turn2 != null

    var onGameFinished: ((Game) -> Unit)? = null
}