package lu.exchange.usecase

interface UnitOfWork {
    fun <T> withTransaction(block: () -> T): T
}
