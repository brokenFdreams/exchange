package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
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

        account.debit(BigDecimal(10.0)).shouldBeRight()

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

        val actualError = account.debit(BigDecimal(-10.0)).shouldBeLeft()

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

        val actualError = account.debit(BigDecimal(10.0)).shouldBeLeft()

        actualError shouldBeEqual InsufficientBalanceError
    }
}