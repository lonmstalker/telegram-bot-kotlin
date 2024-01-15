package io.lonmstalker.bot.out.bot.model

data class CallbackData(
    val offset: Int?,
    val value: String?,
    val command: String,
    val prevValue: String?
)
