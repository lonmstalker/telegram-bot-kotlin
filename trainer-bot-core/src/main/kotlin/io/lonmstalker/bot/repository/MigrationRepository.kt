package io.lonmstalker.bot.repository

import io.lonmstalker.bot.out.bot.model.tables.records.MigrationInfoRecord

interface MigrationRepository {
    fun insert(record: MigrationInfoRecord)
    fun findAllExecutedMigrationNames(): List<String>
}