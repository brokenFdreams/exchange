package lu.exchange.domain

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.equals.shouldBeEqual
import lu.exchange.domain.command.TransferCommand
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TestTransfer {

    @Test
    fun transfer() {
        val from = createAccount(randomPositiveLong(), "EUR", BigDecimal(10.0)).shouldBeRight()
        val to = createAccount(randomPositiveLong(), "EUR", BigDecimal(0.0)).shouldBeRight()
        val command = TransferCommand(from.id, to.id, BigDecimal(10.0))

        transferService.transfer(command).shouldBeRight()

        accountRepository.getAccountById(from.id).shouldBeEqual(from)
        from.balance.shouldBeEqual(Balance(BigDecimal(0.0)))
        accountRepository.getAccountById(to.id).shouldBeEqual(to)
        to.balance.shouldBeEqual(Balance(BigDecimal(10.0)))
    }

}