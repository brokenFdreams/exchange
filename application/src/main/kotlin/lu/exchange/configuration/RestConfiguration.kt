package lu.exchange.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    SwaggerConfiguration::class,
    MvcConfiguration::class,
)
class RestConfiguration