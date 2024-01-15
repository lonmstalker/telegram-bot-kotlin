package io.lonmstalker.bot.config

import org.springframework.boot.autoconfigure.context.MessageSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.util.Locale

@Configuration
class MessageConfig {

    @Bean
    @ConfigurationProperties("spring.messages")
    fun messageSourceProperties() = MessageSourceProperties()

    @Bean
    fun messageSource(messageSourceProperties: MessageSourceProperties): MessageSource =
        ReloadableResourceBundleMessageSource()
            .apply {
                this.setDefaultLocale(Locale.of("ru"))
                this.setDefaultEncoding(messageSourceProperties.encoding.name())
                this.setUseCodeAsDefaultMessage(messageSourceProperties.isUseCodeAsDefaultMessage)
                this.setBasenames(*(messageSourceProperties.basename.split(",").map { "classpath:$it" }.toTypedArray()))
            }
}