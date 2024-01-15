package io.lonmstalker.bot.helper

import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.objects.MessageContext
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

@Component
class TelegramHelper(
    private val messageHelper: MessageHelper
) {

    fun sendTextMessage(
        ctx: MessageContext,
        code: String,
        keyboard: ReplyKeyboard? = null,
        args: Array<Any>? = null
    ) {
        SendMessage.builder()
            .chatId(ctx.chatId())
            .text(messageHelper.getMessage(ctx, code, args))
            .replyMarkup(keyboard)
            .build()
            .let { ctx.bot().sender().execute(it) }
    }

    fun sendEditReplyMarkup(ctx: MessageContext, msgId: Int, keyboard: InlineKeyboardMarkup) {
        EditMessageReplyMarkup.builder()
            .chatId(ctx.chatId())
            .messageId(msgId)
            .replyMarkup(keyboard)
            .build()
            .let { ctx.bot().sender().execute(it) }
    }

    fun sendOrEditMessage(
        ctx: MessageContext,
        msg: String,
        msgId: Int? = null,
        keyboard: InlineKeyboardMarkup
    ) {
        if (msgId == null) {
            this.sendTextMessage(
                ctx,
                msg,
                keyboard
            )
        } else {
            this.sendEditReplyMarkup(
                ctx,
                msgId,
                keyboard
            )
        }
    }
}