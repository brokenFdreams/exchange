package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
import lu.exchange.domain.command.CreditCommand
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TestCreditAccount {

    @Test
    fun credit() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(0.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        val command = CreditCommand(BigDecimal(10.0))

        account.credit(command).shouldBeRight()

        account.balance.value.shouldBeEqual(BigDecimal(10.0))
    }

    @Test
    fun `credit with negative amount`() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(10.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        val command = CreditCommand(BigDecimal(-10.0))

        val actualError = account.credit(command).shouldBeLeft()

        actualError shouldBeEqual NegativeAmountError
    }
}