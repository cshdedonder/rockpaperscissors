package cshdedonder.rockpaperscissors.server.handling

import cshdedonder.rockpaperscissors.protocol.*
import cshdedonder.rockpaperscissors.server.model.Game
import cshdedonder.rockpaperscissors.server.model.Model
import org.apache.mina.core.session.IoSession

class SessionHandler(private val session: IoSession){

    val writeCallback: (Message) -> Unit = {m -> session.write(m)}

    var stateHandler: StateHandler = InitialState(this)

    fun handleExternal(message: Any?) {
        (message as? Message)?.let {
            stateHandler.handleExternal(it)
        }
    }

    fun dispose() {
    }
}

interface StateHandler {
    fun handleExternal(message: Message)
}

class InitialState(private val sessionHandler: SessionHandler): StateHandler {

    override fun handleExternal(message: Message) {
        when(message) {
            is CreateGame -> {
                val nextState = WaitingState(sessionHandler)
                Model.createGame(message.player, message.action).onGameFinished = {g -> nextState.receiveResult(g)}
                sessionHandler.stateHandler = nextState
            }
            is JoinGame -> {
                Model.joinGame(message.opponent, message.name, message.action)?.let {
                    sessionHandler.stateHandler = ResultState(sessionHandler, it)
                    sessionHandler.writeCallback.invoke(ResultAvailable)
                }
            }
        }
    }
}

class WaitingState(private val sessionHandler: SessionHandler): StateHandler {

    override fun handleExternal(message: Message) {}

    fun receiveResult(game: Game){
        sessionHandler.stateHandler = ResultState(sessionHandler, game)
        sessionHandler.writeCallback.invoke(ResultAvailable)
    }
}

class ResultState(private val sessionHandler: SessionHandler, private val game: Game) : StateHandler {

    override fun handleExternal(message: Message){
        when(message){
            is RequestResult -> {
                when(message.player) {
                    1 -> sessionHandler.writeCallback.invoke(GameResult(game.turn1.name, game.turn2!!.name, game.turn1.action, game.turn2!!.action, game.resultPlayer1!!))
                    2 -> sessionHandler.writeCallback.invoke(GameResult(game.turn1.name, game.turn2!!.name, game.turn1.action, game.turn2!!.action, game.resultPlayer2!!))
                }
            }
        }
    }
}

