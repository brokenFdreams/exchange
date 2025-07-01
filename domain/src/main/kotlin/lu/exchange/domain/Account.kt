package lu.exchange.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.base.AggregateRoot
import lu.exchange.common.types.base.Version
import lu.exchange.common.types.error.BusinessError
import lu.exchange.domain.command.CreditCommand
import lu.exchange.domain.command.DebitCommand
import java.math.BigDecimal

fun interface AccountIdGenerator {
    fun generate(): AccountId
}

class Account(
    accountId: AccountId,
    val ownerId: OwnerId,
    val currency: Currency,
    private var _balance: Balance,
    version: Version
) : AggregateRoot<AccountId>(accountId, version) {
    val balance: Balance get() = _balance

    companion object {
        fun create(
            testAccountIdGenerator: AccountIdGenerator,
            ownerId: Long,
            currency: String,
            balance: BigDecimal
        ): Either<BusinessError, Account> {
            if (Currency.notContain(currency)) return InvalidCurrencyError.left()
            if (balance < BigDecimal.ZERO) return NegativeBalanceError.left()

            return Account(
                accountId = testAccountIdGenerator.generate(),
                ownerId = OwnerId(ownerId),
                currency = Currency.valueOf(currency),
                _balance = Balance(balance),
                Version.new()
            ).right()
        }
    }

    fun debit(command: DebitCommand): Either<BusinessError, Account> {
        if (command.amount <= BigDecimal.ZERO) return NegativeAmountError.left()
        if (balance.value < command.amount) return InsufficientBalanceError.left()

        _balance = Balance(_balance.value.minus(command.amount))

        return this.right()
    }

    fun credit(command: CreditCommand): Either<BusinessError, Account> {
        if (command.amount <= BigDecimal.ZERO) return NegativeAmountError.left()

        _balance = Balance(_balance.value.plus(command.amount))

        return this.right()
    }
}

object InvalidCurrencyError : BusinessError
object NegativeBalanceError : BusinessError

object InsufficientBalanceError : BusinessError

//Better to validate on command level, as it's not a business error actually
object NegativeAmountError : BusinessError

