package lu.exchange.domain.service

import arrow.core.Either
import lu.exchange.domain.command.TransferCommand
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider
import lu.exchange.domain.provider.ExchangeRateProvider

class TransferService(
    accountProvider: AccountProvider,
    accountPersister: AccountPersister,
    exchangeRateProvider: ExchangeRateProvider
) {
    fun transfer(command: TransferCommand): Either<Error, Unit> {
        TODO("Not implemented yet!")
    }
}
