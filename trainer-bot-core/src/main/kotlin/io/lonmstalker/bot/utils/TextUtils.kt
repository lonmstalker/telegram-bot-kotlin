package io.lonmstalker.bot.utils

object TextUtils {
    private val ageNumbers = arrayOf('2', '3', '4')

    fun ageToText(age: Number): String =
        age.toString().let {
            if (ageNumbers.contains(it.last())) {
                "$it года"
            } else {
                "$it лет"
            }
        }
}