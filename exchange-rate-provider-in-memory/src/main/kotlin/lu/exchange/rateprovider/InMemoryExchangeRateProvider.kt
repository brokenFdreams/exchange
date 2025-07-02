package lu.exchange.rateprovider

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.Currency
import lu.exchange.domain.provider.ExchangeRateProvider
import lu.exchange.domain.provider.UnavailableExchangeRateProvider
import java.math.BigDecimal

class InMemoryExchangeRateProvider : ExchangeRateProvider {
    private val exchangeMap = mapOf(
        Currency.EUR to mapOf(
            Currency.USD to BigDecimal(0.9)
        ),
        Currency.USD to mapOf(
            Currency.EUR to BigDecimal(1.1)
        ),
        Currency.RUB to mapOf(
            Currency.EUR to BigDecimal(90),
            Currency.USD to BigDecimal(85)
        ),
        Currency.GEL to mapOf(
            Currency.EUR to BigDecimal(3.1),
            Currency.USD to BigDecimal(2.9)
        )
    )

    override fun getExchangeRate(
        from: Currency,
        toCurrency: Currency
    ): Either<BusinessError, BigDecimal> {
        if (exchangeMap.contains(from) && exchangeMap.getValue(from).contains(toCurrency)) {
            return exchangeMap.getValue(from).getValue(toCurrency).right()
        }
        return UnavailableExchangeRateProvider.left()
    }
}