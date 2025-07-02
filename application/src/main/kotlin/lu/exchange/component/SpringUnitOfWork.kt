package lu.exchange.component

import lu.exchange.usecase.UnitOfWork
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SpringUnitOfWork : UnitOfWork {

    @Transactional
    override fun <T> withTransaction(block: () -> T): T {
        return block()
    }
}