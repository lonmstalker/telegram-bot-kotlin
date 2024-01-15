package io.lonmstalker.bot.exception.handler

import io.lonmstalker.bot.constants.ErrorConstants.SERVER_ERROR
import io.lonmstalker.bot.exception.BaseException
import io.lonmstalker.bot.helper.MessageHelper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.User

@Component
class ExceptionHandler(private val messageHelper: MessageHelper) {

    fun handle(user: User, ex: Exception, sender: DefaultAbsSender) {
        createMessage(user, ex)
            .apply { this.setChatId(user.id) }
            .let {
                try {
                    sender.execute(it)
                } catch (ex: Exception) {
                    log.error("catch error when try resolve ex:", ex)
                }
            }
    }

    private fun createMessage(user: User, ex: Exception): SendMessage {
        val msg = SendMessage()

        if (ex is BaseException) {
            msg.text = messageHelper.getMessage(user, ex)
        } else {
            msg.text = messageHelper.getMessage(user, SERVER_ERROR)
            log.error("catch error: ", ex)
        }

        return msg
    }

    companion object{
        private val log = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }
}