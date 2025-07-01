package lu.exchange.domain

import lu.exchange.common.types.base.ValueObject

data class AccountId(
    private val value: Long
) : ValueObject {
    fun toLongValue() = value
}