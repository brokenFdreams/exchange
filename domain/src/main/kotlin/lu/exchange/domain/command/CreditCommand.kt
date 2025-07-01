package lu.exchange.domain.command

import lu.exchange.common.types.base.Command
import java.math.BigDecimal

data class CreditCommand(val amount: BigDecimal) : Command
