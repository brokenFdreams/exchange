package lu.exchange.persistence

import lu.exchange.common.types.base.Version
import lu.exchange.domain.Account
import lu.exchange.domain.AccountId
import lu.exchange.domain.Balance
import lu.exchange.domain.Currency
import lu.exchange.domain.OwnerId
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface JdbcAccountRepository : CrudRepository<AccountEntity, Long> {
    @Modifying
    @Query(
        """
        INSERT INTO account (id, owner_id, currency, balance, version)
        VALUES (:#{#account.id}, :#{#account.ownerId}, :#{#account.currency}, :#{#account.balance}, :#{#account.version})
    """
    )
    fun insert(@Param("account") account: AccountEntity)

    @Modifying
    @Query(
        """
        UPDATE account 
        SET balance = :#{#account.balance}, version = :#{#account.version}
        WHERE id = :#{#account.id} AND version = :oldVersion
    """
    )
    fun update(@Param("account") account: AccountEntity, @Param("oldVersion") oldVersion: Long): Boolean
}

@Table("account")
data class AccountEntity(
    @Id
    val id: Long,
    val ownerId: Long,
    val currency: String,
    val balance: BigDecimal,
    val version: Long,
)

fun AccountEntity.toDomain() = Account(
    accountId = AccountId(id),
    ownerId = OwnerId(ownerId),
    currency = Currency.valueOf(currency),
    _balance = Balance(balance),
    version = Version.from(version)
)

fun Account.toEntity() = AccountEntity(
    id = id.toLongValue(),
    ownerId = ownerId.toLongValue(),
    currency = currency.name,
    balance = balance.value,
    version = version.toLongValue()
)