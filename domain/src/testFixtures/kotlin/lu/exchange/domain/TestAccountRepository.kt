package lu.exchange.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.provider.AccountNotFoundError
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider

class TestAccountRepository : AccountProvider, AccountPersister {

    private val accounts = mutableMapOf<AccountId, Account>()

    override fun getAccountById(id: AccountId): Either<BusinessError, Account> {
        return accounts[id]?.right() ?: AccountNotFoundError.left()
    }

    override fun persist(account: Account) {
        accounts[account.id] = account.copy()
    }
}

private fun Account.copy(): Account {
    return Account(
        accountId = id,
        ownerId = ownerId,
        currency = currency,
        _balance = balance,
        version = version
    )
}
