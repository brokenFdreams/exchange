package lu.exchange.domain

import lu.exchange.common.types.base.ValueObject
import java.math.BigDecimal

data class Balance(
    val value: BigDecimal
) : ValueObject