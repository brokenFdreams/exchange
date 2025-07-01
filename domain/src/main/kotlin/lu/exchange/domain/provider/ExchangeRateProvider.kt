package lu.exchange.domain.provider

import arrow.core.Either
import lu.exchange.domain.Currency
import java.math.BigDecimal

fun interface ExchangeRateProvider {
    fun getExchangeRate(from: Currency, toCurrency: Currency): Either<Error, Rate>
}

data class Rate(val value: BigDecimal)
