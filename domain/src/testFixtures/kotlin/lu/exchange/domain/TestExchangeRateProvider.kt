package lu.exchange.domain

import arrow.core.Either
import arrow.core.right
import lu.exchange.domain.provider.ExchangeRateProvider
import lu.exchange.domain.provider.Rate
import java.math.BigDecimal

class TestExchangeRateProvider : ExchangeRateProvider{

    override fun getExchangeRate(
        from: Currency,
        toCurrency: Currency
    ): Either<Error, Rate> {
        return Rate(BigDecimal(1)).right()
    }
}