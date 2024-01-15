package io.lonmstalker.bot

import io.lonmstalker.bot.properties.BotInfoProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(BotInfoProperties::class)
@Import(TelegramBotStarterConfiguration::class)
class TrainerBotMain

fun main(args: Array<String>) {
    runApplication<TrainerBotMain>(*args)
}