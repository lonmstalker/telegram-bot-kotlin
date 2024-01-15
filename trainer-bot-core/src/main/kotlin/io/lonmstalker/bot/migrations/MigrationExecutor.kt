package io.lonmstalker.bot.migrations

import io.lonmstalker.bot.out.bot.model.tables.records.MigrationInfoRecord
import io.lonmstalker.bot.repository.MigrationRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MigrationExecutor(
    @Lazy private val executor: MigrationExecutor,
    private val migrations: List<MigrationService>,
    private val migrationRepository: MigrationRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        val log = LoggerFactory.getLogger(MigrationExecutor::class.java)
        val executedMigrations = migrationRepository.findAllExecutedMigrationNames()
        migrations
            .filter { !executedMigrations.contains(it.javaClass.simpleName) }
            .sortedBy { it.getVersion() }
            .forEach {
                val name = it.javaClass.simpleName
                log.info("started migration {}", name)
                executor.executeAndSaveMigration(it, name)
                log.info("ended migration {}", name)
            }
    }

    @Transactional
    fun executeAndSaveMigration(service: MigrationService, serviceName: String) {
        service.execute()
        migrationRepository.insert(MigrationInfoRecord(name = serviceName))
    }
}