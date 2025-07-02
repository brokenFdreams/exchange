package lu.exchange.usecase

import arrow.core.Either
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.Account
import lu.exchange.domain.AccountIdGenerator
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.usecase.dto.AccountDTO
import lu.exchange.usecase.dto.toDTO
import java.math.BigDecimal

class CreateAccountUseCase(
    private val accountIdGenerator: AccountIdGenerator,
    private val accountPersister: AccountPersister
) {
    fun execute(
        ownerId: Long,
        currency: String,
        balance: BigDecimal
    ): Either<BusinessError, AccountDTO> {
        return Account.create(
            accountIdGenerator,
            ownerId = ownerId,
            currency = currency,
            balance = balance,
        ).map { account -> account.also { accountPersister.persist(it) }.toDTO() }
    }
}
