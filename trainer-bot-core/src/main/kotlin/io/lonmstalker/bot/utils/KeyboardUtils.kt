package io.lonmstalker.bot.utils

import io.lonmstalker.bot.constants.BotConstants.UNDERSCORE
import io.lonmstalker.bot.constants.DbConstants.LIMIT_OFFSET
import io.lonmstalker.bot.enums.DirectionEnum
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object KeyboardUtils {

    fun createInlineValueKeyboardWithDirection(
        current: Int, max: Int,
        vararg buttonRows: List<Pair<Any, String>>
    ): InlineKeyboardMarkup =
        createInlineValueKeyboard(*buttonRows)
            .apply {
                val row = mutableListOf<InlineKeyboardButton>()
                if (current != 0) {
                    row.add(DirectionEnum.BACK.let {
                        createInlineButton(it.directionName, it.callback + UNDERSCORE + current)
                    })
                }
                if (max - current >= LIMIT_OFFSET) {
                    row.add(DirectionEnum.NEXT.let {
                        createInlineButton(it.directionName, it.callback + UNDERSCORE + current)
                    })
                }
                if (row.isNotEmpty()) this.keyboard.add(row)
            }

    fun createInlineValueKeyboard(
        vararg buttonRows: List<Pair<Any, String>>
    ): InlineKeyboardMarkup =
        InlineKeyboardMarkup().apply {
            this.keyboard = buttonRows.map {
                it.map { createInlineButton(it.second, it.first.toString()) }
            }
        }

    fun createInlineKeyboard(vararg buttonRows: KeyboardButtonEnum): InlineKeyboardMarkup =
        InlineKeyboardMarkup().apply {
            this.keyboard = listOf(
                buttonRows.map { createInlineButton(it.text, it.callback!!) }
            )
        }

    fun createInlineKeyboard(vararg buttonRows: List<KeyboardButtonEnum>): InlineKeyboardMarkup =
        InlineKeyboardMarkup().apply {
            this.keyboard = buttonRows.map {
                it.map {
                    InlineKeyboardButton.builder()
                        .text(it.text)
                        .callbackData(it.callback)
                        .build()
                }
            }
        }

    fun createReplyKeyboard(
        vararg buttonRows: KeyboardButtonEnum,
        selective: Boolean = false,
        isPersistent: Boolean = false,
        resizeKeyboard: Boolean = true,
        oneTimeKeyboard: Boolean = false,
        inputFieldPlaceholder: String? = null,
    ): ReplyKeyboardMarkup = createReplyKeyboard(
        listOf(*buttonRows),
        selective = selective,
        isPersistent = isPersistent,
        resizeKeyboard = resizeKeyboard,
        oneTimeKeyboard = oneTimeKeyboard,
        inputFieldPlaceholder = inputFieldPlaceholder
    )

    fun createReplyKeyboard(
        vararg buttonRows: List<KeyboardButtonEnum>,
        selective: Boolean = false,
        isPersistent: Boolean = false,
        resizeKeyboard: Boolean = true,
        oneTimeKeyboard: Boolean = false,
        inputFieldPlaceholder: String? = null
    ): ReplyKeyboardMarkup =
        ReplyKeyboardMarkup().apply {
            this.selective = selective
            this.isPersistent = isPersistent
            this.resizeKeyboard = resizeKeyboard
            this.oneTimeKeyboard = oneTimeKeyboard
            this.inputFieldPlaceholder = inputFieldPlaceholder
            this.keyboard = buttonRows.map {
                KeyboardRow(
                    it.map {
                        KeyboardButton.builder()
                            .text(it.text)
                            .build()
                    }
                )
            }
        }

    private fun createInlineButton(value: String, callback: String) =
        InlineKeyboardButton.builder()
            .text(value)
            .callbackData(callback)
            .build()
}