package lu.exchange.configuration

import lu.exchange.domain.provider.ExchangeRateProvider
import lu.exchange.rateprovider.InMemoryExchangeRateProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExchangeRateConfiguration {

    @Bean
    fun exchangeRateProvider(): ExchangeRateProvider {
        return InMemoryExchangeRateProvider()
    }
}
