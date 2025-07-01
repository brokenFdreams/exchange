package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
import lu.exchange.domain.command.DebitCommand
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TestDebitAccount {

    @Test
    fun debit() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(10.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        val command = DebitCommand(BigDecimal(10.0))

        account.debit(command).shouldBeRight()

        account.balance.value.shouldBeEqual(BigDecimal.ZERO)
    }

    @Test
    fun `debit with negative amount`() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(10.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        val command = DebitCommand(BigDecimal(-10.0))

        val actualError = account.debit(command).shouldBeLeft()

        actualError shouldBeEqual NegativeAmountError
    }

    @Test
    fun `debit with insufficient balance`() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(0.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        val command = DebitCommand(BigDecimal(10.0))

        val actualError = account.debit(command).shouldBeLeft()

        actualError shouldBeEqual InsufficientBalanceError
    }
}