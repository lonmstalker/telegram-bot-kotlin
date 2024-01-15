package io.lonmstalker.bot.helper

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Component
class ClockHelper(private val clock: Clock) {

    fun clockInstant(): Instant = this.clock.instant()
    fun clock(): LocalDateTime = LocalDateTime.now(this.clock)
    fun clockOffset(): OffsetDateTime = OffsetDateTime.now(this.clock)
}