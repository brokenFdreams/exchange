package lu.exchange.domain.provider

import arrow.core.Either
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.Currency
import java.math.BigDecimal

fun interface ExchangeRateProvider {
    //TODO: Not a BusinessError
    //TODO: Return a Rate object, not the BigDecimal
    fun getExchangeRate(from: Currency, toCurrency: Currency): Either<BusinessError, BigDecimal>
}

object UnavailableExchangeRateProvider : BusinessError
