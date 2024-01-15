package io.lonmstalker.bot.repository.impl

import io.lonmstalker.bot.out.bot.model.tables.records.UserInfoRecord
import io.lonmstalker.bot.out.bot.model.tables.references.USER_INFO
import io.lonmstalker.bot.repository.UserRepository
import org.jooq.DSLContext
import org.jooq.TableField
import org.jooq.impl.DSL.field
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val ctx: DSLContext) : UserRepository {

    override fun getUser(id: Long): UserInfoRecord? =
        ctx
            .selectFrom(USER_INFO)
            .where(USER_INFO.ID.eq(id))
            .fetch()
            .firstOrNull()

    override fun <T> updateField(id: Long, field: TableField<UserInfoRecord, T>, value: T) =
        ctx.update(USER_INFO)
            .set(field(field.name), value)
            .where(USER_INFO.ID.eq(id))
            .execute()

    @Suppress("UNCHECKED_CAST")
    override fun updateFields(id: Long, pairs: Array<out Pair<TableField<UserInfoRecord, *>, *>>): Unit =
        ctx.batched {
            it.dsl().apply {
                pairs.forEach {
                    this.update(USER_INFO)
                        .set(field((it.first as TableField<UserInfoRecord, Any?>).name), it.second)
                        .where(USER_INFO.ID.eq(id))
                        .execute()
                }
            }
        }

    override fun insert(user: UserInfoRecord): UserInfoRecord =
        ctx
            .executeInsert(user)
            .let { user }
}