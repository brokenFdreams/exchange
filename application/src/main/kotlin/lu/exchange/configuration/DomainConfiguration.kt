package lu.exchange.configuration

import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider
import lu.exchange.domain.provider.ExchangeRateProvider
import lu.exchange.domain.service.TransferService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {

    @Bean
    fun transferService(
        accountProvider: AccountProvider,
        accountPersister: AccountPersister,
        exchangeRateProvider: ExchangeRateProvider
    ) = TransferService(
        accountProvider = accountProvider,
        accountPersister = accountPersister,
        exchangeRateProvider = exchangeRateProvider
    )
}