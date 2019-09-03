package cshdedonder.rockpaperscissors.protocol

import java.io.Serializable

sealed class Message : Serializable

class JoinGame(val opponent: String, val name: String, val action: Action): Message()
class GameResult(val player1: String, val player2: String, val action1: Action, val action2: Action, val playerResult: Result): Message()
class CreateGame(val player: String, val action: Action): Message()
object WaitingForOtherPlayer: Message()
object ResultAvailable: Message()
class RequestResult(val player: Int): Message()