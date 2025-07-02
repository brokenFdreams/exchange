package lu.exchange.usecase

import arrow.core.Either
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.AccountId
import lu.exchange.domain.command.TransferCommand
import lu.exchange.domain.service.TransferService
import java.math.BigDecimal

class TransferUseCase(
    private val transferService: TransferService,
    private val unitOfWork: UnitOfWork
) {
    fun execute(fromAccountId: Long, toAccountId: Long, amount: BigDecimal): Either<BusinessError, Unit> {
        return unitOfWork.withTransaction {
            transferService.transfer(
                TransferCommand(
                    from = AccountId(fromAccountId),
                    to = AccountId(toAccountId),
                    amount = amount
                )
            )
        }
    }
}