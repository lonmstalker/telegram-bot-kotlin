package io.lonmstalker.bot.out.bot.model

import com.google.common.base.MoreObjects
import com.google.common.base.Objects
import com.google.common.base.Preconditions.checkArgument
import com.google.common.collect.Lists
import org.slf4j.LoggerFactory
import org.telegram.abilitybots.api.bot.BaseAbilityBot
import org.telegram.abilitybots.api.objects.Locality
import org.telegram.abilitybots.api.objects.MessageContext
import org.telegram.abilitybots.api.objects.Privacy
import org.telegram.abilitybots.api.objects.Reply
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.function.BiConsumer
import java.util.function.Predicate


class Ability @SafeVarargs private constructor(
    private val name: String,
    private val info: String?,
    locality: Locality?,
    privacy: Privacy?,
    argNum: Int,
    statsEnabled: Boolean,
    action: ((MessageContext) -> Unit)?,
    postAction: ((MessageContext) -> Unit)?,
    replies: List<Reply>,
    vararg flags: (Update) -> Boolean
) {
    private val locality: Locality
    private val privacy: Privacy
    private val argNum: Int
    private val statsEnabled: Boolean
    private val action: ((MessageContext) -> Unit)
    private val postAction: ((MessageContext) -> Unit)?
    private val replies: List<Reply>
    private val flags: List<(Update) -> Boolean>

    init {
        val log = LoggerFactory.getLogger(Ability::class.java)
        this.locality =
            checkNotNull(locality) { "Please specify a valid locality setting. Use the Locality enum class" }
        this.privacy =
            checkNotNull(privacy) { "Please specify a valid privacy setting. Use the Privacy enum class" }
        checkArgument(
            argNum >= 0,
            "The number of arguments the method can handle CANNOT be negative. Use the number 0 if the method ignores the arguments OR uses as many as appended"
        )
        this.argNum = argNum
        this.action =
            checkNotNull(action) { "Method action can't be empty. Please assign a function by using .action() method" }
        if (postAction == null) {
            log.info(
                String.format(
                    "No post action was detected for method with name [%s]",
                    name
                )
            )
        }
        this.flags = listOf(*flags)
        this.postAction = postAction
        this.replies = replies
        this.statsEnabled = statsEnabled
    }

    fun name(): String = name

    fun info(): String? = info

    fun locality(): Locality = locality

    fun privacy(): Privacy = privacy

    fun tokens(): Int = argNum

    fun statsEnabled(): Boolean = statsEnabled

    fun action(): ((MessageContext) -> Unit) = action

    fun postAction(): ((MessageContext) -> Unit)? = postAction

    fun replies(): List<Reply> = replies

    fun flags(): List<(Update) -> Boolean> = flags

    override fun toString(): String {
        return MoreObjects.toStringHelper(this).add("name", name).add("locality", locality).add(
            "privacy",
            privacy
        ).add("argNum", argNum).toString()
    }

    override fun equals(o: Any?): Boolean {
        return if (this === o) {
            true
        } else if (o != null && this.javaClass == o.javaClass) {
            val ability = o as Ability
            argNum == ability.argNum && Objects.equal(
                name,
                ability.name
            ) && locality == ability.locality && privacy == ability.privacy
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return java.util.Objects.hash(
            *arrayOf(
                name,
                info, locality, privacy, argNum, action, postAction, replies, flags
            )
        )
    }

    class AbilityBuilder internal constructor() {
        private lateinit var name: String
        private var info: String? = null
        private var privacy: Privacy? = null
        private var locality: Locality? = null
        private var argNum = 0
        private var statsEnabled = false
        private var action: ((MessageContext) -> Unit)? = null
        private var postAction: ((MessageContext) -> Unit)? = null
        private val replies: MutableList<Reply>
        private var flags: Array<out (Update) -> Boolean> = arrayOf()

        init {
            replies = Lists.newArrayList()
        }

        fun action(consumer: ((MessageContext) -> Unit)?): AbilityBuilder {
            action = consumer
            return this
        }

        fun name(name: String): AbilityBuilder {
            this.name = name
            return this
        }

        fun info(info: String?): AbilityBuilder {
            this.info = info
            return this
        }

        fun flag(vararg flags: (Update) -> Boolean): AbilityBuilder {
            this.flags = flags
            return this
        }

        fun locality(type: Locality?): AbilityBuilder {
            locality = type
            return this
        }

        fun input(argNum: Int): AbilityBuilder {
            this.argNum = argNum
            return this
        }

        fun enableStats(): AbilityBuilder {
            statsEnabled = true
            return this
        }

        fun setStatsEnabled(statsEnabled: Boolean): AbilityBuilder {
            this.statsEnabled = statsEnabled
            return this
        }

        fun privacy(privacy: Privacy?): AbilityBuilder {
            this.privacy = privacy
            return this
        }

        fun post(postAction: ((MessageContext) -> Unit)?): AbilityBuilder {
            this.postAction = postAction
            return this
        }

        @SafeVarargs
        fun reply(
            action: BiConsumer<BaseAbilityBot?, Update?>?,
            vararg conditions: Predicate<Update?>?
        ): AbilityBuilder {
            replies.add(Reply.of(action, *conditions))
            return this
        }

        fun reply(reply: Reply): AbilityBuilder {
            replies.add(reply)
            return this
        }

        fun basedOn(ability: Ability): AbilityBuilder {
            replies.clear()
            replies.addAll(ability.replies())
            return name(ability.name()).info(ability.info()).input(ability.tokens()).locality(ability.locality())
                .privacy(ability.privacy()).action(ability.action()).post(ability.postAction())
        }

        fun build(): Ability {
            return Ability(
                name,
                info,
                locality, privacy, argNum, statsEnabled, action, postAction, replies, *flags
            )
        }
    }

    companion object {
        fun builder(): AbilityBuilder = AbilityBuilder()
    }
}

