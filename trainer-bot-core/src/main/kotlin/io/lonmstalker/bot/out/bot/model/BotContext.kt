package io.lonmstalker.bot.out.bot.model

import io.lonmstalker.bot.enums.StateEnum
import io.lonmstalker.bot.out.bot.CustomAbilityBot
import org.telegram.telegrambots.meta.api.objects.User

data class BotContext(
    val chatId: Long,
    val user: User,
    val currentState: StateEnum,
    val bot: CustomAbilityBot
)