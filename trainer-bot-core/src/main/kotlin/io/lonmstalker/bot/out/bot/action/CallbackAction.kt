package io.lonmstalker.bot.out.bot.action

import io.lonmstalker.bot.out.bot.model.BotContext
import io.lonmstalker.bot.out.bot.model.BotResponse
import io.lonmstalker.bot.out.bot.model.CallbackData

interface CallbackAction {

    fun invoke(ctx: BotContext, data: CallbackData): BotResponse

    fun getCommand(): String
}