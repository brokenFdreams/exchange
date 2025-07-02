package lu.exchange.rest

import lu.exchange.common.types.error.BusinessError
import lu.exchange.usecase.CreateAccountUseCase
import lu.exchange.usecase.GetAccountByIdUseCase
import lu.exchange.usecase.dto.AccountDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

sealed class AccountResult {
    data class Success(val accountResponse: AccountResponse) : AccountResult()
    data class Error(val businessError: BusinessError) : AccountResult()
}

@RestController("accounts")
class AccountController(
    private val getAccountUseCase: GetAccountByIdUseCase,
    private val createAccountUseCase: CreateAccountUseCase
) {
    @PostMapping
    fun createAccount(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResult> {
        createAccountUseCase.execute(
            request.ownerId,
            request.currency,
            request.initialBalance
        ).fold(
            ifLeft = { error -> return ResponseEntity.unprocessableEntity().body(AccountResult.Error(error)) },
            ifRight = { accountDTO -> return ResponseEntity.ok(AccountResult.Success(accountDTO.toResponse())) }
        )
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: Long): ResponseEntity<AccountResult> {
        getAccountUseCase.execute(id).fold(
            ifLeft = { error -> return ResponseEntity.unprocessableEntity().body(AccountResult.Error(error)) },
            ifRight = { accountDTO -> return ResponseEntity.ok(AccountResult.Success(accountDTO.toResponse())) }
        )
    }
}

data class CreateAccountRequest(val ownerId: Long, val currency: String, val initialBalance: BigDecimal)

data class AccountResponse(
    val accountId: Long,
    val ownerId: Long,
    val currency: String,
    val balance: BigDecimal
)

fun AccountDTO.toResponse() = AccountResponse(
    accountId = accountId,
    ownerId = ownerId,
    currency = currency.name,
    balance = balance
)
