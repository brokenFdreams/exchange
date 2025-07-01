package lu.exchange.domain

import arrow.core.Either
import arrow.core.right
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.provider.ExchangeRateProvider
import java.math.BigDecimal

class TestExchangeRateProvider : ExchangeRateProvider{

    override fun getExchangeRate(
        from: Currency,
        toCurrency: Currency
    ): Either<BusinessError, BigDecimal> {
        return BigDecimal(1).right()
    }
}