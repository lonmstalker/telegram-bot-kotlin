package io.lonmstalker.bot.out.bot.action

import io.lonmstalker.bot.out.bot.model.BotContext
import io.lonmstalker.bot.out.bot.model.BotResponse

interface CommandAction {
    fun getCommand(): String
    fun invoke(ctx: BotContext): BotResponse
}