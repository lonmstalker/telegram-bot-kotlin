package io.lonmstalker.bot.repository

import io.lonmstalker.bot.out.bot.model.tables.records.UserInfoRecord
import org.jooq.TableField

interface UserRepository {

    fun getUser(id: Long): UserInfoRecord?

    fun <T> updateField(id: Long, field: TableField<UserInfoRecord, T>, value: T): Int

    fun updateFields(id: Long, pairs: Array<out Pair<TableField<UserInfoRecord, *>, *>>)

    fun insert(user: UserInfoRecord): UserInfoRecord
}