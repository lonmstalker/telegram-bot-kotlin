package io.lonmstalker.bot.out.bot

import io.lonmstalker.bot.constants.BotConstants.DEFAULT_COMMAND
import io.lonmstalker.bot.enums.StateEnum
import io.lonmstalker.bot.exception.handler.ExceptionHandler
import io.lonmstalker.bot.out.bot.action.CallbackAction
import io.lonmstalker.bot.out.bot.action.CommandAction
import io.lonmstalker.bot.out.bot.action.StateAction
import io.lonmstalker.bot.out.bot.model.BotContext
import io.lonmstalker.bot.out.bot.model.BotResponse
import io.lonmstalker.bot.properties.BotInfoProperties
import io.lonmstalker.bot.service.StateService
import io.lonmstalker.bot.utils.CallbackUtils.parseCallback
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.db.DBContext
import org.telegram.abilitybots.api.util.AbilityUtils
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.system.measureTimeMillis

@Component
class CustomAbilityBot(
    private val db: DBContext,
    private val props: BotInfoProperties,
    private val stateService: StateService,
    private val stateActions: List<StateAction>,
    private val exceptionHandler: ExceptionHandler,
    private val commands: Map<String, CommandAction>,
    private val callbacks: Map<String, CallbackAction>
) : TelegramLongPollingBot(DefaultBotOptions(), props.token) {

    override fun getBotUsername(): String = props.name

    override fun onUpdateReceived(update: Update) {
        log.info("New update {}", update.updateId)
        log.debug("{}", update)
        val executeTime = measureTimeMillis {
            val ctx = BotContext(AbilityUtils.getChatId(update), AbilityUtils.getUser(update), this)
            try {
                execute(update, ctx)
                    .also { it.nextState?.apply { stateService.setState(ctx, this) } }
                    .run { super.execute(this.method) }
                db.commit()
            } catch (ex: Exception) {
                exceptionHandler.handle(ctx.user, ex, this)
            }
        }
        log.debug("Processing time: [{} ms]", executeTime)
    }

    private fun execute(update: Update, ctx: BotContext): BotResponse {
        if (update.hasCallbackQuery()) {
            val callback = update.parseCallback()
            val command = callbacks[callback.command]
            if (command != null) {
                return command.invoke(ctx, callback)
            }
        } else {
            val command = commands[update.message.text]
            if (command != null) {
                return command.invoke(ctx)
            }
        }
        return invokeStep(ctx) ?: commands[DEFAULT_COMMAND]!!.invoke(ctx)
    }

    private fun invokeStep(ctx: BotContext): BotResponse? =
        stateService.getState(ctx)
            ?.let { StateEnum.byId(it) }
            ?.let { state ->
                stateActions.asSequence()
                                .filter { it.isSupport(state) }
                                .firstOrNull()
                                ?.invoke(ctx)
            }

    companion object {
        private val log = LoggerFactory.getLogger(CustomAbilityBot::class.java)
    }
}