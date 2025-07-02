package lu.exchange.configuration

import lu.exchange.domain.AccountIdGenerator
import lu.exchange.domain.provider.AccountPersister
import lu.exchange.domain.provider.AccountProvider
import lu.exchange.domain.service.TransferService
import lu.exchange.usecase.CreateAccountUseCase
import lu.exchange.usecase.GetAccountByIdUseCase
import lu.exchange.usecase.TransferUseCase
import lu.exchange.usecase.UnitOfWork
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfiguration {

    @Bean
    fun createAccountUseCase(
        accountIdGenerator: AccountIdGenerator,
        accountPersister: AccountPersister
    ) = CreateAccountUseCase(accountIdGenerator, accountPersister)

    @Bean
    fun getAccountByIdUseCase(
        accountProvider: AccountProvider
    ) = GetAccountByIdUseCase(accountProvider)

    @Bean
    fun transferUseCase(
        transferService: TransferService,
        unitOfWork: UnitOfWork
    ) = TransferUseCase(transferService, unitOfWork)
}
