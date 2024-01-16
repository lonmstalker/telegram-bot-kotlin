package io.lonmstalker.bot.service

import io.lonmstalker.bot.constants.DbConstants.STATE_NAME
import io.lonmstalker.bot.enums.StateEnum
import io.lonmstalker.bot.out.bot.model.BotContext
import org.springframework.stereotype.Service
import org.telegram.abilitybots.api.db.DBContext

@Service
class StateService(db: DBContext) {
    private val states: MutableMap<Long, Int> = db.getMap(STATE_NAME)

    fun getState(chatId: Long): Int = states[chatId] ?: 0

    fun setState(ctx: BotContext, state: StateEnum) {
        states[ctx.chatId] = state.id
    }
}