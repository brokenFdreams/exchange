package lu.exchange.domain

import lu.exchange.domain.service.TransferService
import java.math.BigDecimal
import kotlin.random.Random


fun randomPositiveLong() = Random.nextLong(from = 0, until = Long.MAX_VALUE)

val accountIdGenerator = TestAccountIdGenerator()

val accountRepository = TestAccountRepository()

val transferService = TransferService(
    accountProvider = accountRepository,
    accountPersister = accountRepository,
    exchangeRateProvider = TestExchangeRateProvider()
)

fun createAccount(
    ownerId: Long = randomPositiveLong(),
    currency: String = "EUR",
    balance: BigDecimal = BigDecimal(0.0)
    ) = Account.create(accountIdGenerator, ownerId, currency, balance)
        .onRight { accountRepository.persist(it) }
