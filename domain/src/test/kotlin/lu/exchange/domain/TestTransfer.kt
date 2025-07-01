package lu.exchange.domain

import arrow.core.Either
import arrow.core.left
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.command.TransferCommand
import lu.exchange.domain.provider.AccountNotFoundError
import lu.exchange.domain.provider.ExchangeRateProvider
import lu.exchange.domain.provider.UnavailableExchangeRateProvider
import lu.exchange.domain.service.TransferService
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TestTransfer {

    @Test
    fun `transfer without exchange rate`() {
        val from = createAccount(randomPositiveLong(), "EUR", BigDecimal(10.0)).shouldBeRight()
        val to = createAccount(randomPositiveLong(), "EUR", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(from.id, to.id, BigDecimal(10.0))

        transferService.transfer(command).shouldBeRight()

        val fromSaved = accountRepository.getAccountById(from.id).shouldBeRight()
        fromSaved.balance.shouldBeEqual(Balance(BigDecimal(0.0)))
        val toSaved = accountRepository.getAccountById(to.id).shouldBeRight()
        toSaved.balance.shouldBeEqual(Balance(BigDecimal(10.0)))
    }

    @Test
    fun `transfer with exchange rate`() {
        val from = createAccount(randomPositiveLong(), "USD", BigDecimal(10.0)).shouldBeRight()
        val to = createAccount(randomPositiveLong(), "EUR", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(from.id, to.id, BigDecimal(10.0))

        transferService.transfer(command).shouldBeRight()

        val fromSaved = accountRepository.getAccountById(from.id).shouldBeRight()
        fromSaved.balance.shouldBeEqual(Balance(BigDecimal(0.0)))
        val toSaved = accountRepository.getAccountById(to.id).shouldBeRight()
        toSaved.balance.shouldBeEqual(Balance(BigDecimal(10.0)))
    }

    @Test
    fun `transfer without from account`() {
        val to = createAccount(randomPositiveLong(), "EUR", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(getNextAccountId(), to.id, BigDecimal(10.0))

        val result = transferService.transfer(command)

        result.shouldBeLeft().shouldBeEqual(AccountNotFoundError)
    }

    @Test
    fun `transfer without to account`() {
        val from = createAccount(randomPositiveLong(), "EUR", BigDecimal(9.0)).shouldBeRight()
        val command = TransferCommand(from.id, getNextAccountId(), BigDecimal(10.0))

        val result = transferService.transfer(command)

        result.shouldBeLeft().shouldBeEqual(AccountNotFoundError)
    }

    @Test
    fun `transfer with insufficient balance`() {
        val from = createAccount(randomPositiveLong(), "EUR", BigDecimal(9.0)).shouldBeRight()
        val to = createAccount(randomPositiveLong(), "EUR", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(from.id, to.id, BigDecimal(10.0))

        val result = transferService.transfer(command)

        result.shouldBeLeft().shouldBeEqual(InsufficientBalanceError)
    }

    @Test
    fun `transfer with unavailable ExchangeRateProvider`() {
        val from = createAccount(randomPositiveLong(), "EUR", BigDecimal(10.0)).shouldBeRight()
        val to = createAccount(randomPositiveLong(), "USD", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(from.id, to.id, BigDecimal(10.0))

        val transferService = TransferService(
            accountProvider = accountRepository,
            accountPersister = accountRepository,
            exchangeRateProvider = object : ExchangeRateProvider {
                override fun getExchangeRate(
                    from: Currency,
                    toCurrency: Currency
                ): Either<BusinessError, BigDecimal> {
                    return UnavailableExchangeRateProvider.left()
                }
            }
        )

        val result = transferService.transfer(command)

        result.shouldBeLeft().shouldBeEqual(UnavailableExchangeRateProvider)
    }

}