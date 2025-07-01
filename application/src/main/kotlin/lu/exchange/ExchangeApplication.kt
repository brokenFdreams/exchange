package lu.exchange

import lu.exchange.configuration.ApplicationConfiguration
import org.springframework.boot.runApplication


fun main(args: Array<String>) {
    runApplication<ApplicationConfiguration>(*args)
}