package lu.exchange.common.types.base

abstract class DomainEntity<T> protected constructor(
    val id: T,
    private var _version: Version
) {
    val version: Version get() = _version

    private var events = mutableListOf<DomainEvent>()

    protected fun addEvent(event: DomainEvent) {
        if (events.isEmpty()) {
            _version = _version.next()
        }
        events.add(event)
    }

    fun popEvents(): List<DomainEvent> {
        val res = events
        events = mutableListOf()
        return res
    }

    protected fun nextVersion() {
        _version = version.next()
    }
}

data class Version(private val value: Long) : ValueObject {

    fun toLongValue() = value

    fun next() = Version(value + 1)

    fun previous() = Version(value - 1)

    companion object {
        fun new() = Version(0L)
        fun from(value: Long) = Version(value)
    }
}
