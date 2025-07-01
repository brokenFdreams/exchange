package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
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

        account.credit(BigDecimal(10.0)).shouldBeRight()

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

        val actualError = account.credit(BigDecimal(-10.0)).shouldBeLeft()

        actualError shouldBeEqual NegativeAmountError
    }
}