package lu.exchange.rest

import lu.exchange.common.types.error.BusinessError
import lu.exchange.usecase.TransferUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal


sealed class TransferResult {
    class Success : TransferResult()
    data class Error(val businessError: BusinessError) : TransferResult()
}

@RestController("transfers")
class TransferController(
    private val transferUseCase: TransferUseCase
) {
    @PostMapping
    fun transfer(@RequestBody request: TransferRequest): ResponseEntity<TransferResult> {
        transferUseCase.execute(
            request.fromAccountId,
            request.toAccountId,
            request.amount
        ).fold(
            ifLeft = { error -> return ResponseEntity.unprocessableEntity().body(TransferResult.Error(error)) },
            ifRight = { _ -> return ResponseEntity.ok(TransferResult.Success()) }
        )
    }
}

data class TransferRequest(val fromAccountId: Long, val toAccountId: Long, val amount: BigDecimal)
