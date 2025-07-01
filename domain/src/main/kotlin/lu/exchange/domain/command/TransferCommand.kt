package lu.exchange.domain.command

import lu.exchange.common.types.base.Command
import lu.exchange.domain.AccountId
import java.math.BigDecimal

data class TransferCommand(
    val from: AccountId,
    val to: AccountId,
    val amount: BigDecimal
) : Command