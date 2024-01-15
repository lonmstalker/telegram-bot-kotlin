package io.lonmstalker.bot.service

import io.lonmstalker.bot.out.bot.model.tables.records.UserInfoRecord
import io.lonmstalker.bot.repository.UserRepository
import org.jooq.TableField
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.User

@Service
class UserService(private val userRepository: UserRepository) {

    @Cacheable("users", key = "#id", unless = "#result == null")
    fun getUser(id: Long): UserInfoRecord? = userRepository.getUser(id)

    @CacheEvict("users", key = "#id")
    fun <T> updateField(id: Long, field: TableField<UserInfoRecord, T>, value: T): Int =
        userRepository.updateField(id, field, value)

    @CacheEvict("users", key = "#id")
    fun updateFields(id: Long, vararg pairs: Pair<TableField<UserInfoRecord, *>, *>) =
        userRepository.updateFields(id, pairs)

    fun insert(user: User): UserInfoRecord =
        userRepository.insert(UserInfoRecord(id = user.id, locale = user.languageCode, completeStart = false))
}