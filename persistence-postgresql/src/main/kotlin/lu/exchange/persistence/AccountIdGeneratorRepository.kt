package lu.exchange.persistence

import lu.exchange.domain.AccountId
import lu.exchange.domain.AccountIdGenerator
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class AccountIdGeneratorRepository(
    dataSource: DataSource
) : AccountIdGenerator {

    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun generate() = AccountId(
        jdbcTemplate.queryForObject(
            "SELECT nextval('account_id_sequence')"
        ) { rs, _ -> rs.getLong(1) }
            ?: throw GenerateIdFailed
    )
}

object GenerateIdFailed : Exception("Failed to generate next ID from sequence") {
    private fun readResolve(): Any = GenerateIdFailed
}