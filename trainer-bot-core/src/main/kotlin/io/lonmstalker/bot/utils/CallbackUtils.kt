package io.lonmstalker.bot.utils

import io.lonmstalker.bot.out.bot.model.CallbackData
import org.telegram.telegrambots.meta.api.objects.Update

object CallbackUtils {
    private const val VALUE = "value"
    private const val OFFSET = "offset"
    private const val COMMAND = "command"
    private const val PREV_VALUE = "prevValue"

    private val valuePattern = ":".toPattern()
    private val callbackPattern = ";".toPattern()

    fun Update.parseCallback(): CallbackData =
        this.callbackQuery
            .data
            .split(callbackPattern)
            .map { it.split(valuePattern) }
            .associate { it[0] to it[1] }
            .let {
                CallbackData(
                    command = it[COMMAND]!!,
                    value = it[VALUE],
                    offset = it[OFFSET]?.toIntOrNull(),
                    prevValue = it[PREV_VALUE]
                )
            }

    fun CallbackData.toData(): String {
        val sb = StringBuilder(COMMAND)
            .append(":")
            .append(command)
        if (value != null) {
            sb.append(";").append(VALUE).append(":").append(value)
        }
        if (prevValue != null) {
            sb.append(";").append(PREV_VALUE).append(":").append(prevValue)
        }
        if (offset != null) {
            sb.append(";").append(OFFSET).append(":").append(offset)
        }
        return sb.toString()
    }

}