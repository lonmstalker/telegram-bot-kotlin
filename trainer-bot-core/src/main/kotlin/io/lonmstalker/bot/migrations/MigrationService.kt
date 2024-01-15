package io.lonmstalker.bot.migrations

interface MigrationService {
    fun execute()
    fun getVersion(): Int
}
