package lu.exchange.usecase.dto

import lu.exchange.domain.Account
import lu.exchange.domain.Currency
import java.math.BigDecimal

data class AccountDTO(
    val accountId: Long,
    val ownerId: Long,
    val currency: Currency,
    val balance: BigDecimal
)

fun Account.toDTO() = AccountDTO(
    accountId = id.toLongValue(),
    ownerId = ownerId.toLongValue(),
    currency = currency,
    balance = balance.value,
)
