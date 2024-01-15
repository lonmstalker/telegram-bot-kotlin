package io.lonmstalker.bot.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.cache")
    fun cacheProperties() = CacheProperties()

    @Bean
    fun cacheManager(cacheProperties: CacheProperties) =
        CaffeineCacheManager().apply { this.setCaffeine(caffeine(cacheProperties)) }

    @Bean
    fun caffeine(cacheProperties: CacheProperties): Caffeine<Any, Any> =
        Caffeine.from(cacheProperties.caffeine.spec)
}