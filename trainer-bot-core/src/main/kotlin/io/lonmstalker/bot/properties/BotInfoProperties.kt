package io.lonmstalker.bot.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(value = "app.bot")
data class BotInfoProperties @ConstructorBinding constructor(
    val name: String,
    val token: String,
    val adminId: Long
)