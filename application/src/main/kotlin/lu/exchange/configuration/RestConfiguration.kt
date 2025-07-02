package lu.exchange.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    MvcConfiguration::class,
    SwaggerConfiguration::class
)
@ComponentScan(basePackages = ["lu.exchange.rest"])
class RestConfiguration