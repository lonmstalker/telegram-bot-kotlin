package io.lonmstalker.bot.repository.impl

import io.lonmstalker.bot.out.bot.model.tables.records.MigrationInfoRecord
import io.lonmstalker.bot.out.bot.model.tables.references.MIGRATION_INFO
import io.lonmstalker.bot.repository.MigrationRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MigrationRepositoryImpl(private val ctx: DSLContext) : MigrationRepository {

    override fun insert(record: MigrationInfoRecord) {
        ctx.insertInto(MIGRATION_INFO)
            .set(MIGRATION_INFO.NAME, record.name)
            .execute()
    }

    override fun findAllExecutedMigrationNames(): List<String> =
        ctx.select(MIGRATION_INFO.NAME)
            .from(MIGRATION_INFO)
            .fetchArray()
            .map { it.value1()!! }
}