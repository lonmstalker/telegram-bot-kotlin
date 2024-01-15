package io.lonmstalker.bot.out.bot.model

import io.lonmstalker.bot.enums.StateEnum
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import java.io.Serializable

data class BotResponse(
    val method: BotApiMethod<Serializable>,
    val nextState: StateEnum?
) {

    class Builder {
        private var method: BotApiMethod<Serializable>? = null
        private var nextState: StateEnum? = null

        fun method(m: BotApiMethod<Serializable>?) =
            this.apply { method = m }

        fun nextState(m: StateEnum) =
            this.apply { nextState = m }

        fun build() = BotResponse(method!!, nextState)
    }
}