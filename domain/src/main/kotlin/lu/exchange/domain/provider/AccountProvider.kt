package lu.exchange.domain.provider

import arrow.core.Either
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.Account
import lu.exchange.domain.AccountId

fun interface AccountProvider {
    fun getAccountById(id: AccountId): Either<BusinessError, Account>
}

object AccountNotFoundError : BusinessError