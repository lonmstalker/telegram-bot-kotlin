package io.lonmstalker.bot.out.bot.action

import io.lonmstalker.bot.enums.StateEnum
import io.lonmstalker.bot.out.bot.model.BotContext
import io.lonmstalker.bot.out.bot.model.BotResponse

interface StateAction {
    fun invoke(ctx: BotContext): BotResponse
    fun isSupport(state: StateEnum): Boolean
}