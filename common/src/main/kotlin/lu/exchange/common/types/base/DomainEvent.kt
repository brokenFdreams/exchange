package lu.exchange.common.types.base

import java.time.OffsetDateTime
import java.util.UUID

abstract class DomainEvent protected constructor() {
    val id = EventId.generate()
    val created: OffsetDateTime = OffsetDateTime.now()
}

data class EventId(val value: UUID) {
    fun toStringValue() = value.toString()

    companion object {
        fun generate(): EventId = EventId(UUID.randomUUID())
    }
}
