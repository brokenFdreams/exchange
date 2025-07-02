package lu.exchange.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(
    DomainConfiguration::class,
    UseCaseConfiguration::class,
    RestConfiguration::class
)
class ApplicationConfiguration
