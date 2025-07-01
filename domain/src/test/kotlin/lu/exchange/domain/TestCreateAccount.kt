package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import lu.exchange.common.types.error.BusinessError
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCreateAccount {

    @Test
    fun `create account`() {
        val ownerId = randomPositiveLong()
        val currency = "EUR"
        val balance = BigDecimal(0.0)

        val account = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeRight()

        account.id.shouldNotBeNull()
        account.ownerId.toLongValue().shouldBeEqual(ownerId)
        account.currency.shouldBeEqual(Currency.EUR)
        account.balance.shouldBeEqual(Balance(balance))
    }

    @ParameterizedTest
    @MethodSource("createAccountWithError")
    fun `create account with error`(
        ownerId: Long,
        currency: String,
        balance: BigDecimal,
        expectedError: BusinessError
    ) {
        val error = Account.create(
            TestAccountIdGenerator(),
            ownerId,
            currency,
            balance
        ).shouldBeLeft()

        error shouldBeEqual expectedError
    }

    companion object {
        @JvmStatic
        fun createAccountWithError(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    randomPositiveLong(),
                    "",
                    BigDecimal(0.0),
                    InvalidCurrencyError
                ),
                Arguments.of(
                    randomPositiveLong(),
                    "BLABLA",
                    BigDecimal(0.0),
                    InvalidCurrencyError
                ),
                Arguments.of(
                    randomPositiveLong(),
                    "EUR",
                    BigDecimal(-10.0),
                    NegativeBalanceError
                )
            )
        }
    }
}