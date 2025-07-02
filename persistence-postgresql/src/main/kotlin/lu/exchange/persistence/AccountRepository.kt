package lu.exchange.persistence

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.base.Version
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.Account
import lu.exchange.domain.AccountId
import lu.exchange.domain.provider.AccountNotFoundError
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    private val jdbcAccountRepository: JdbcAccountRepository
) : AccountProvider, AccountPersister {
    override fun getAccountById(id: AccountId): Either<BusinessError, Account> {
        return jdbcAccountRepository.findByIdOrNull(id.toLongValue())?.toDomain()?.right()
            ?: AccountNotFoundError.left()
    }

    override fun persist(account: Account) {
        if (account.version == Version.new()) {
            jdbcAccountRepository.insert(account.toEntity())
        } else {
            jdbcAccountRepository.save(account.toEntity())
        }
    }
}