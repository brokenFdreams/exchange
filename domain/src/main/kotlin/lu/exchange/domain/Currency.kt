package lu.exchange.domain

import lu.exchange.common.types.base.ValueObject

//I think the enum is better than a String
enum class Currency : ValueObject {
    USD,
    EUR,
    GEL,
    RUB;

    companion object {
        private val values = entries.map { it.name }

        fun notContain(value: String) = !values.contains(value)
    }
}