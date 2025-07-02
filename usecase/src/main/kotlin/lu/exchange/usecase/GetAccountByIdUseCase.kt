package lu.exchange.usecase

import arrow.core.Either
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.AccountId
import lu.exchange.domain.provider.AccountProvider
import lu.exchange.usecase.dto.AccountDTO
import lu.exchange.usecase.dto.toDTO

class GetAccountByIdUseCase(
    private var accountProvider: AccountProvider
) {
    fun execute(accountId: Long): Either<BusinessError, AccountDTO> {
        return accountProvider.getAccountById(AccountId(accountId))
            .map { account -> account.toDTO() }
    }
}