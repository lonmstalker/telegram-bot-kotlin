package io.lonmstalker.bot.out.bot.state_machine

import io.lonmstalker.bot.enums.StateEnum
import io.lonmstalker.bot.out.bot.model.BotContext
import io.lonmstalker.bot.out.bot.model.BotResponse
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import java.io.Serializable

class StateRegistry {
    private val actions: MutableList<StateAction> = mutableListOf()

    fun action(
        currentState: StateEnum,
        nextState: StateEnum,
        action: (BotContext) -> BotApiMethod<Serializable>
    ): StateRegistry =
        this
            .apply { actions.add(StateAction(nextState, currentState, action)) }

    fun invoke(action: BotContext): BotResponse =
        actions
            .first { it.currentState == action.currentState }
            .let { BotResponse(it.action.invoke(action), it.nextState) }

    data class StateAction(
        val nextState: StateEnum,
        val currentState: StateEnum,
        val action: (BotContext) -> BotApiMethod<Serializable>
    )
}