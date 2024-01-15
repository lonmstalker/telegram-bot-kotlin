package io.lonmstalker.bot.enums

import org.apache.commons.lang3.EnumUtils

enum class StateEnum(val id: Int, val prevId: Int = 1) {
    START(1)
    ;

    companion object {
        private val stateMap: Map<Int, StateEnum> = EnumUtils.getEnumMap(StateEnum::class.java)
            .map { it.value.id to it.value }
            .toMap()

        fun byId(id: Int): StateEnum? = stateMap[id]
    }
}