package cshdedonder.rockpaperscissors.protocol

import cshdedonder.rockpaperscissors.protocol.Result.*

enum class Action {
    ROCK {
        override fun against(action: Action): Result = when(action) {
            ROCK -> DRAW
            PAPER -> LOSS
            SCISSORS -> WIN
        }
    }, PAPER {
        override fun against(action: Action): Result = when(action) {
            ROCK -> WIN
            PAPER -> DRAW
            SCISSORS -> LOSS
        }
    }, SCISSORS {
        override fun against(action: Action): Result = when(action) {
            ROCK -> LOSS
            PAPER -> WIN
            SCISSORS -> DRAW
        }
    };

    abstract fun against(action: Action): Result
}

enum class Result {
    WIN, LOSS, DRAW
}