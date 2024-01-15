package io.lonmstalker.bot.config

import io.lonmstalker.bot.properties.BotInfoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.abilitybots.api.db.DBContext
import org.telegram.abilitybots.api.db.MapDBContext

@Configuration
class DbConfig {

    @Bean
    fun dbContext(props: BotInfoProperties): DBContext = MapDBContext.onlineInstance(props.name)
}