package lu.exchange.domain

import lu.exchange.common.types.base.ValueObject

data class OwnerId(
    private val value: Long
) : ValueObject {
    fun toLongValue() = value
}