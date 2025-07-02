package lu.exchange.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import lu.exchange.common.types.base.AggregateRoot
import lu.exchange.common.types.base.Version
import lu.exchange.common.types.error.BusinessError
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
            accountIdGenerator: AccountIdGenerator,
            ownerId: Long,
            currency: String,
            balance: BigDecimal
        ): Either<BusinessError, Account> {
            if (Currency.notContain(currency)) return InvalidCurrencyError.left()
            if (balance < BigDecimal.ZERO) return NegativeBalanceError.left()

            return Account(
                accountId = accountIdGenerator.generate(),
                ownerId = OwnerId(ownerId),
                currency = Currency.valueOf(currency),
                _balance = Balance(balance),
                Version.new()
            ).right()
        }
    }

    internal fun debit(amount: BigDecimal): Either<BusinessError, Unit> {
        if (amount <= BigDecimal.ZERO) return NegativeAmountError.left()
        if (balance.value < amount) return InsufficientBalanceError.left()

        _balance = Balance(_balance.value.minus(amount))

        return Unit.right()
    }

    internal fun credit(amount: BigDecimal): Either<BusinessError, Unit> {
        if (amount <= BigDecimal.ZERO) return NegativeAmountError.left()

        _balance = Balance(_balance.value.plus(amount))

        return Unit.right()
    }
}

object InvalidCurrencyError : BusinessError
object NegativeBalanceError : BusinessError

object InsufficientBalanceError : BusinessError

//Better to validate on command level, as it's not a business error actually
object NegativeAmountError : BusinessError

