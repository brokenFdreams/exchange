package lu.exchange.domain.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.command.TransferCommand
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider
import lu.exchange.domain.provider.ExchangeRateProvider

class TransferService(
    private val accountProvider: AccountProvider,
    private val accountPersister: AccountPersister,
    private val exchangeRateProvider: ExchangeRateProvider
) {
    fun transfer(command: TransferCommand): Either<BusinessError, Unit> {
        val fromAccount = accountProvider.getAccountById(command.from)
            .fold(
                ifLeft = { error -> return error.left() },
                ifRight = { account -> account }
            )
        val toAccount = accountProvider.getAccountById(command.to)
            .fold(
                ifLeft = { error -> return error.left() },
                ifRight = { account -> account }
            )

        val creditAmount = if (fromAccount.currency == toAccount.currency)
            command.amount
        else
            exchangeRateProvider.getExchangeRate(fromAccount.currency, toAccount.currency).fold(
                ifLeft = { error -> return error.left() },
                ifRight = { rate -> rate.multiply(command.amount)  }
            )

        fromAccount.debit(command.amount).onLeft { error -> return error.left() }
        toAccount.credit(creditAmount).onLeft { error -> return error.left() }

        accountPersister.persist(fromAccount)
        accountPersister.persist(toAccount)
        return Unit.right()
    }
}
