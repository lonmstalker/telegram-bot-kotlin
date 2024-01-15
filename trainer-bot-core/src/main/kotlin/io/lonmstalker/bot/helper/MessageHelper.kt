package io.lonmstalker.bot.helper

import io.lonmstalker.bot.exception.BaseException
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.objects.MessageContext
import org.telegram.telegrambots.meta.api.objects.User
import java.util.Locale

@Component
class MessageHelper(private val messageSource: MessageSource) {

    fun getMessage(
        user: User,
        code: String,
        args: Array<Any>? = null,
        defaultMessage: String? = null
    ): String =
        user.languageCode
            .let { this.messageSource.getMessage(code, args, defaultMessage, Locale.of(it)) ?: UNAVAILABLE_LOCALE }

    fun getMessage(
        ctx: MessageContext,
        code: String,
        args: Array<Any>? = null,
        defaultMessage: String? = null
    ): String = getMessage(ctx.user(), code, args, defaultMessage)

    fun getMessage(
        ctx: MessageContext,
        ex: BaseException
    ) = this.getMessage(ctx, ex.code, ex.args, ex.message)

    fun getMessage(
        user: User,
        ex: BaseException
    ) = this.getMessage(user, ex.code, ex.args, ex.message)

    companion object {
        private const val UNAVAILABLE_LOCALE = "unavailable locale for bot"
    }
}