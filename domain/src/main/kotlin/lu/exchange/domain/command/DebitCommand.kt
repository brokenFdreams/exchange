package lu.exchange.domain.command

import lu.exchange.common.types.base.Command
import java.math.BigDecimal

data class DebitCommand(val amount: BigDecimal) : Command
