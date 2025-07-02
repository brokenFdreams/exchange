package lu.exchange.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(
    DomainConfiguration::class,
    UseCaseConfiguration::class,
    RestConfiguration::class,
    PersistenceConfiguration::class,
    ExchangeRateConfiguration::class
)
@ComponentScan(basePackages = ["lu.exchange.component"])
class ApplicationConfiguration
