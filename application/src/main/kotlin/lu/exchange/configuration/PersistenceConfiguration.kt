package lu.exchange.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback

@Configuration
@ComponentScan(basePackages = ["lu.exchange.persistence"])
@EnableJdbcRepositories(basePackages = ["lu.exchange.persistence"])
class PersistenceConfiguration {

    @Bean
    fun beforeConvertCallback() = BeforeConvertCallback<Any> { aggregate -> aggregate }

}