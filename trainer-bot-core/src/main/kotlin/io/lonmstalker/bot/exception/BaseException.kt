package io.lonmstalker.bot.exception

abstract class BaseException(
    val msg: String,
    val code: String,
    val args: Array<Any>? = null,
    cause0: Throwable? = null,
) : RuntimeException(msg, cause0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseException

        if (msg != other.msg) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode() = 31 * msg.hashCode() + code.hashCode()
}